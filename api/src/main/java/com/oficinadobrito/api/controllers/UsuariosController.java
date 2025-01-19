package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.*;
import com.oficinadobrito.api.services.*;
import com.oficinadobrito.api.utils.dtos.usuario.EmailDto;
import com.oficinadobrito.api.utils.dtos.usuario.UpdateUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("usuarios")
public class UsuariosController {

    private final NotificacoesService notificacoesService;
    private final FeedbacksService feedbacksService;
    private final PagamentosService pagamentosService;
    private final ImoveisService imoveisService;
    private final PlanosService planosService;
    private final UsuariosService usuariosService;
    private final AnunciosService anunciosService;
    private static final String NOT_EXISTS = "Not exists usuario with id informed";

    public UsuariosController(NotificacoesService notificacoesService, FeedbacksService feedbacksService,AnunciosService anunciosService, PagamentosService pagamentosService, PlanosService planosService, UsuariosService usuariosService,ImoveisService imoveisService) {
        this.notificacoesService = notificacoesService;
        this.feedbacksService = feedbacksService;
        this.pagamentosService = pagamentosService;
        this.planosService = planosService;
        this.usuariosService = usuariosService;
        this.anunciosService = anunciosService;
        this.imoveisService = imoveisService;
    }

    @Operation(summary = "get all usuarios")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Usuario>> getAllUsuarios(){
        List<Usuario> usuarios = this.usuariosService.getAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "get One usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getOneUsuario(@PathVariable("id") String id){
       Usuario usuario = this.usuariosService.findUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "get One usuario By email (barer token) -> role USER ")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/email")
    public ResponseEntity<Usuario> getOneUsuarioByEmail(@RequestBody @Valid EmailDto emailDto){
        Usuario usuario = this.usuariosService.findUsuarioByEmail(emailDto.email());
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "put one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") String id, @RequestBody @Valid UpdateUsuarioDto usuarioDto) {
        Usuario usuarioUpdate = Usuario.updateDtoToEntity(usuarioDto);
        Usuario usuario = this.usuariosService.updateUsuario(id,usuarioUpdate);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "get all notifications of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/notificacoes")
    public ResponseEntity<Set<Notificacao>> getAllNotificaoesForUsuarioId(@PathVariable("id") String id) {
        Set<Notificacao> notificacoes = notificacoesService.getAllNotificationForUsuario(id);

        if (notificacoes.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(notificacoes);
    }

    @Operation(summary = "get all notifications of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/imoveis")
    public ResponseEntity<Set<Imovel>> getAllImoveisForUsuarioId(@PathVariable("id") String id) {
        Set<Imovel> imoveis = this.imoveisService.getAllImoveisForUsuario(id);

        if (imoveis.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(imoveis);
    }

    @Operation(summary = "get all feedbacks of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/feedbacks")
    public ResponseEntity<Set<Feedback>> getAllFeedbacksForUsuarioId(@PathVariable("id") String id) {
        Set<Feedback> feedbacks = feedbacksService.getAllFeedbacksForUsuario(id);

        if (feedbacks.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(feedbacks);
    }

    @Operation(summary = "get all pagamentos of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}/pagamentos")
    public ResponseEntity<Set<Pagamento>> getAllPagamentosForUsuarioId(@PathVariable("id") String id) {
        Set<Pagamento> feedbacks = pagamentosService.getAllPagamentosForUsuario(id);

        if (feedbacks.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(feedbacks);
    }

    @Operation(summary = "get all planos of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/{id}/planos")
    public ResponseEntity<Set<Plano>> getAllPlanosForUsuarioId(@PathVariable("id") String id) {
        Set<Plano> planos = planosService.getAllPlanosForUsuario(id);

        if (planos.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(planos);
    }

    @Operation(summary = "get all anuncios of one usuario")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/{id}/anuncios")
    public ResponseEntity<Set<Anuncio>> getAllAnunciosForUsuarioId(@PathVariable("id") String id) {
        Set<Anuncio> anuncios = this.anunciosService.getAllAnunciosForUsuario(id);

        if (anuncios.isEmpty()) {
            throw new ResourceNotFoundException(NOT_EXISTS);
        }

        return ResponseEntity.ok(anuncios);
    }

    @Operation(summary = "Allows a user with an administrator role to consult a user's CPF.")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/cpf")
    public ResponseEntity<String> verifyCpf(@PathVariable("id") String id) {
        String cpf = this.usuariosService.searchCpf(id);
        return ResponseEntity.ok(cpf);
    }

}
