package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.entities.ProcessDataInfo;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.ImovelRepository;
import com.oficinadobrito.api.repositories.TarefaRepository;
import com.oficinadobrito.api.utils.dtos.imovel.AvaliacaoDto;
import org.apache.coyote.BadRequestException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;
import org.springframework.core.io.FileSystemResource;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImoveisService extends GenericService<Imovel> {

    private final UsuariosService usuariosService;
    private final ImovelRepository imoveisRepository;
    private final TarefaRepository tarefaRepository;
    private final Path rootLocation = Paths.get("uploads");
    private final ConfiabilidateCalculatorService calculadorService;

    public ImoveisService(ImovelRepository repository,UsuariosService usuariosService,ImovelRepository imoveisRepository, TarefaRepository tarefaRepository,ConfiabilidateCalculatorService calculadorService) {
        super(repository);
        this.usuariosService = usuariosService;
        this.imoveisRepository = imoveisRepository;
        this.tarefaRepository = tarefaRepository;
        this.calculadorService =calculadorService;
    }

    public Set<Imovel> getAllImoveisForUsuario(String usuarioId){
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        return usuario == null? new HashSet<>() : new HashSet<>(this.imoveisRepository.findByUsuarioId(usuarioId));
    }

    public void savePdf(BigInteger id, MultipartFile file) throws IOException {
        if (file.isEmpty()) {throw new BadRequestException("File is empty");}
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        
        String fileName = id + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(fileName);
        Files.copy(file.getInputStream(), destinationFile);
        
        Imovel imovel = this.imoveisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("imovel not found"));
        imovel.setPdfFileName(fileName);
        this.imoveisRepository.save(imovel);
    }

    public Resource loadPdfFile(BigInteger id) {
        Imovel imovel = this.imoveisRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Imovel not found"));
        String fileName = imovel.getPdfFileName();
        if (fileName == null || fileName.isEmpty()) {
            throw new ResourceNotFoundException("No PDF file associated with this property");
        }
        Path filePath = rootLocation.resolve(fileName).normalize();
        if (!filePath.startsWith(rootLocation)) {
            throw new SecurityException("Access to this file is not allowed!");
        }
        if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
            throw new ResourceNotFoundException("File not found or not readable");
        }
        return new FileSystemResource(filePath);
    }


    public AvaliacaoDto getAvaliacao(BigInteger imovelId){
        AvaliacaoDto avaliacao = new AvaliacaoDto();
        ProcessDataInfo processado = this.tarefaRepository.findFirstByImovelIdIsNotNull().orElseThrow(()-> new ResourceNotFoundException("Not found process with this imovelId"));
        
        Imovel imovel = this.findById(imovelId);
        String textoAnalisar = processado.getResultado();
        Imovel imovelAnalisado = extrairDados(textoAnalisar);
        double confiabilidade = this.calculadorService.calcularConfiabilidade(imovel,imovelAnalisado);
        int quantAcertos = this.calculadorService.calcularQuantidadeAcertos(imovel,imovelAnalisado);
        int quantVerificados = this.calculadorService.calcularQuantidadeVerificados(imovel,imovelAnalisado);
        avaliacao.setIndiceConfiabilidade(confiabilidade);
        avaliacao.setQuantAcerto(quantAcertos);
        avaliacao.setQuantVerificacao(quantVerificados);
        return avaliacao;
    }
    
    private Imovel extrairDados(String textoBruto) {
        Imovel imovel = new Imovel();

        imovel.setLocalizacao(extrairValor(textoBruto, "UNIDADE AUTÔNOMA:.*?no (\\d+º .+?),"));
        imovel.setAreaCalculada(parseDouble(extrairValor(textoBruto, "área total de ([\\d,.]+)m")));
        imovel.setComprimento(parseDouble(extrairValor(textoBruto, "privativa de ([\\d,.]+)m")));
        imovel.setLargura(parseDouble(extrairValor(textoBruto, "área comum total de ([\\d,.]+)m")));
        imovel.setMatriculaCartorio(extrairValor(textoBruto, "matrícula nº (\\d+)"));
        imovel.setNomeProprietario(extrairValor(textoBruto, "PROPRIETÁRIOS: 1\\) (.+?),"));
        imovel.setCpfCnpjProprietario(extrairValor(textoBruto, "CGC/MF sob nº ([\\d/.%-]+)"));
        imovel.setRgProprietario(extrairValor(textoBruto, "RG nº ([\\d.-]+)"));
        imovel.setNomeCartorioRegistrado(extrairValor(textoBruto, "Serviço de Registro de Imóveis (.+?),"));
        imovel.setRestricoes("Nenhuma encontrada");

        return imovel;
    }

    private static String extrairValor(String texto, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(texto);
        return matcher.find() ? matcher.group(1).trim() : "Não encontrado";
    }

    private static Double parseDouble(String valor) {
        return Optional.ofNullable(valor)
                .filter(v -> !v.equals("Não encontrado"))
                .map(v -> v.replace(",", "."))
                .map(Double::parseDouble)
                .orElse(null);
    }

    @Async
    public CompletableFuture<List<String>> extractTextFromPdfAsync(BigInteger imovelId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Imovel imovel = this.imoveisRepository.findById(imovelId).orElseThrow(() -> new ResourceNotFoundException("Imovel not found"));
                String fileName = imovel.getPdfFileName();
                Path filePath = rootLocation.resolve(fileName);

                if (!Files.exists(filePath)) {throw new ResourceNotFoundException("File not found");}

                List<String> extractedTexts = new ArrayList<>();
                try (PDDocument document = PDDocument.load(filePath.toFile())) {

                    // Caso 1. Tenta extrair texto diretamente
                    PDFTextStripper textStripper = new PDFTextStripper();
                    String text = textStripper.getText(document).trim();
                    if (!text.isEmpty()) {
                        extractedTexts.add(text);
                    } else {
                        //Caso 2. Se não houver texto, usa OCR (Tesseract)
                        PDFRenderer pdfRenderer = new PDFRenderer(document);
                        Tesseract tesseract = new Tesseract();
                        tesseract.setDatapath("src/main/resources/tessdata/");
                        tesseract.setLanguage("por");

                        for (int page = 0; page < document.getNumberOfPages(); ++page) {
                            BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300);
                            String result = tesseract.doOCR(bim);
                            extractedTexts.add(result);
                        }
                    }
                }
                return extractedTexts;
            } catch (IOException | TesseractException e) {
                throw new RuntimeException("Erro ao processar PDF", e);
            }
        });
    }
}
