package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.entities.ProcessDataInfo;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.AnunciosService;
import com.oficinadobrito.api.services.ImoveisService;
import com.oficinadobrito.api.services.NetoNosJobsService;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.imovel.AvaliacaoDto;
import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("imoveis")
public class ImoveisController implements IController<Imovel, CreateImovelDto, UpdateImovelDto> {

    private final ImoveisService imoveisService;
    private final AnunciosService anunciosService;
    private final UsuariosService usuariosService;
    private final NetoNosJobsService netoNosJobsService;

    public ImoveisController(ImoveisService imoveisService, UsuariosService usuariosService,
            AnunciosService anunciosService, NetoNosJobsService netoNosJobsService) {
        this.imoveisService = imoveisService;
        this.usuariosService = usuariosService;
        this.anunciosService = anunciosService;
        this.netoNosJobsService = netoNosJobsService;
    }
    
    @Operation(summary = "create one imovel - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    @Override
    public ResponseEntity<Imovel> postResource(@RequestBody @Valid CreateImovelDto resource) {
        Imovel novo = Imovel.createDtoToEntity(resource);
        Usuario usuario = this.usuariosService.findUsuarioById(resource.usuarioId());
        novo.setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.imoveisService.save(novo));
    }

    @Operation(summary = "get one imovel by id - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Imovel> getResourceById(@PathVariable("id") BigInteger id) {
        Imovel imovel = this.imoveisService.findById(id);
        return ResponseEntity.ok(imovel);
    }

    @Operation(summary = "get all imoveis - (barer jwt) -> role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Imovel>> getAllResource() {
        List<Imovel> imoveis = StreamSupport.stream(this.imoveisService.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "update one imovel - (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Imovel> updateResource(@PathVariable("id") BigInteger id, @RequestBody @Valid UpdateImovelDto resource) {
        Imovel imovel = this.anunciosService.updateImovelById(id, resource);
        return ResponseEntity.ok(imovel);
    }

    @Operation(summary = "delete one imovel - (barer jwt) -> role USER")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.imoveisService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "post one imovel")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/uploadPdf")
    public ResponseEntity<String> uploadPdf(@PathVariable("id") BigInteger id,
            @RequestParam("file") MultipartFile file) {
        try {
            this.imoveisService.savePdf(id, file);
            return ResponseEntity.ok("PDF uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload PDF: " + e.getMessage());
        }
    }

    @Operation(summary = "Post one imovel")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getPdf(@PathVariable BigInteger id) throws IOException {
        Resource resource = this.imoveisService.loadPdfFile(id);
        String fileName = resource.getFilename();

        // Retorna o PDF como ResponseEntity com cabeçalho adequado para visualização
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF) // Tipo de mídia para PDF
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Operation(summary = "Extract text from a property PDF")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/extrair-texto-pdf")
    public ResponseEntity<Long> extractTextFromPdf(@PathVariable BigInteger id) {
        ProcessDataInfo tarefa = this.netoNosJobsService.iniciarProcessamento(id);
        return ResponseEntity.ok(tarefa.getProcessId());
    }

    @Operation(summary = "Receive an evaluation of a property to be validated")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/getevaluation")
    public ResponseEntity<AvaliacaoDto> getAvaliacaoImovel(@PathVariable("id") BigInteger id){
        AvaliacaoDto avaliacao = this.imoveisService.getAvaliacao(id);
        return ResponseEntity.ok(avaliacao);
    }

}
