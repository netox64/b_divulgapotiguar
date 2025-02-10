package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.AnuncioRepository;
import com.oficinadobrito.api.utils.dtos.anuncio.UpdateAnuncioDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class AnunciosService extends GenericService<Anuncio> {

    private final UsuariosService usuariosService;
    private final AnuncioRepository anuncioRepository;
    private final ImoveisService imoveisService;
    private final CategoriasService categoriasService;
    private final PlanosService planosService;

    public AnunciosService(AnuncioRepository repository, UsuariosService usuariosService, AnuncioRepository anuncioRepository, ImoveisService imoveisService, CategoriasService categoriasService, PlanosService planosService) {
        super(repository);
        this.usuariosService = usuariosService;
        this.anuncioRepository = anuncioRepository;
        this.imoveisService = imoveisService;
        this.categoriasService = categoriasService;
        this.planosService = planosService;
    }

    public Optional<Anuncio> findAnuncioForId(BigInteger id) {
        return super.repository.findById(id);
    }

    public Set<Anuncio> getAllAnunciosForUsuario(String usuarioId) {
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        return usuario == null ? new HashSet<>() : new HashSet<>(this.anuncioRepository.findByUsuarioId(usuarioId));
    }

    public Anuncio createAnuncio(Anuncio anuncio, BigInteger imovelId, String usuarioId, BigInteger planoId, Set<BigInteger> categoriaIds) {
        Imovel imovel = this.imoveisService.findById(imovelId);
        imovel.setAnunciado(true);
        Plano plano = this.planosService.findById(planoId);
        plano.setQuantAnuncio(plano.getQuantAnuncio() - 1);
        var planoAtualizado = this.planosService.save(plano);
        anuncio.setPlano(planoAtualizado);
        var imovelAtualizado = this.imoveisService.findById(imovel.getImovelId());
        anuncio.setImovel(imovelAtualizado);
        Usuario usuario = this.usuariosService.findUsuarioById(usuarioId);
        anuncio.setUsuario(usuario);
        if (categoriaIds != null) {
            anuncio.setCategorias(this.categoriasService.getAllCategoriaWithIds(categoriaIds));
        }
        return this.save(anuncio);
    }

    public Anuncio updateById(BigInteger anuncioId, UpdateAnuncioDto updateAnuncioDto) {
        Anuncio anuncioAtualizar = this.findById(anuncioId);
        // Atualiza campos de String
        updateField(updateAnuncioDto.title(), anuncioAtualizar::setTitle);
        updateField(updateAnuncioDto.descricao(), anuncioAtualizar::setDescricao);
        updateField(updateAnuncioDto.imagemPropaganda(), anuncioAtualizar::setImagemPropaganda);
        updateField(updateAnuncioDto.tipoPagamento(), anuncioAtualizar::setTipoPagamento);

        // Atualiza campos numéricos
        updateNumberField(updateAnuncioDto.stars(), anuncioAtualizar::setStars, 0.0);
        updateDoubleField(updateAnuncioDto.preco(), anuncioAtualizar::setPreco, 0.01);

        // Atualiza data do anúncio
        if (updateAnuncioDto.dataAnuncio() != null && !updateAnuncioDto.dataAnuncio().isBefore(LocalDate.now())) {
            anuncioAtualizar.setDataAnuncio(updateAnuncioDto.dataAnuncio());
        }

        // Atualiza Imóvel
        if (updateAnuncioDto.imovelId() != null) {
            anuncioAtualizar.setImovel(this.imoveisService.findById(updateAnuncioDto.imovelId()));
        }

        // Atualiza Categorias
        if (updateAnuncioDto.categoriasIds() != null && !updateAnuncioDto.categoriasIds().isEmpty()) {
            updateAnuncioDto.categoriasIds()
                .forEach(categoriaId -> anuncioAtualizar.addCategoria(this.categoriasService.findById(categoriaId)));
        }

        return this.save(anuncioAtualizar);
    }
    private void updateField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
    
    private <T extends Number & Comparable<T>> void updateNumberField(T newValue, Consumer<T> setter, T minValue) {
        if (newValue != null && newValue.compareTo(minValue) >= 0) {
            setter.accept(newValue);
        }
    }

    private void updateDoubleField(Double newValue, Consumer<Double> setter, double minValue) {
        if (newValue != null && newValue >= minValue) {
            setter.accept(newValue);
        }
    }

    public Imovel updateImovelById(BigInteger imovelId, UpdateImovelDto updateImovelDto) {
        Imovel imovelAtualizar = this.imoveisService.findById(imovelId);

        if (updateImovelDto.localizacao() != null && !updateImovelDto.localizacao().isEmpty()) {
            imovelAtualizar.setLocalizacao(updateImovelDto.localizacao());
        }
        if (updateImovelDto.comprimento() != null && updateImovelDto.comprimento() >= 1.0) {
            imovelAtualizar.setComprimento(updateImovelDto.comprimento());
        }
        if (updateImovelDto.largura() != null && updateImovelDto.largura() >= 1.0) {
            imovelAtualizar.setLargura(updateImovelDto.largura());
        }
        if (updateImovelDto.imagemImovel() != null && !updateImovelDto.imagemImovel().isEmpty()) {
            imovelAtualizar.setImagemImovel(updateImovelDto.imagemImovel());
        }
        if (updateImovelDto.tipo() != null && !updateImovelDto.tipo().isEmpty()) {
            imovelAtualizar.setTipo(updateImovelDto.tipo());
        }
        if (updateImovelDto.matriculaCartorio() != null && !updateImovelDto.matriculaCartorio().isEmpty()) {
            imovelAtualizar.setMatriculaCartorio(updateImovelDto.matriculaCartorio());
        }
        if (updateImovelDto.nomeProprietario() != null && !updateImovelDto.nomeProprietario().isEmpty()) {
            imovelAtualizar.setNomeProprietario(updateImovelDto.nomeProprietario());
        }
        if (updateImovelDto.cpfCnpjProprietario() != null && !updateImovelDto.cpfCnpjProprietario().isEmpty()) {
            imovelAtualizar.setCpfCnpjProprietario(updateImovelDto.cpfCnpjProprietario());
        }

        if (updateImovelDto.rgProprietario() != null && !updateImovelDto.rgProprietario().isEmpty()) {
            imovelAtualizar.setRgProprietario(updateImovelDto.rgProprietario());
        }

        if (updateImovelDto.restricoes() != null && !updateImovelDto.restricoes().isEmpty()) {
            imovelAtualizar.setRestricoes(updateImovelDto.restricoes());
        }

        if (updateImovelDto.nomeCartorioRegistrado() != null && !updateImovelDto.nomeCartorioRegistrado().isEmpty()) {
            imovelAtualizar.setNomeCartorioRegistrado(updateImovelDto.nomeCartorioRegistrado());
        }

        if (updateImovelDto.assinaturaControle() != null && !updateImovelDto.assinaturaControle().isEmpty()) {
            imovelAtualizar.setAssinaturaControle(updateImovelDto.assinaturaControle());
        }

        if (updateImovelDto.isAnunciado() != null) {
            imovelAtualizar.setAnunciado(updateImovelDto.isAnunciado());
        }

        if (updateImovelDto.status() != null) {
            imovelAtualizar.setStatus(updateImovelDto.status());
        }

        if (updateImovelDto.sobre() != null && !updateImovelDto.sobre().isEmpty()) {
            imovelAtualizar.setSobre(updateImovelDto.sobre());
        }
        if (updateImovelDto.anuncioId() != null) {
            imovelAtualizar.setAnuncio(this.findById(updateImovelDto.anuncioId()));
        }

        return this.imoveisService.save(imovelAtualizar);
    }

}
