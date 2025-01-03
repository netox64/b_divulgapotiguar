package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.*;
import com.oficinadobrito.api.services.*;
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
    private final PlanosService planosService;
    private final UsuariosService usuariosService;
    private static final String NOT_EXISTS = "Not exists usuario with id informed";

    public UsuariosController(NotificacoesService notificacoesService, FeedbacksService feedbacksService, PagamentosService pagamentosService, PlanosService planosService, UsuariosService usuariosService) {
        this.notificacoesService = notificacoesService;
        this.feedbacksService = feedbacksService;
        this.pagamentosService = pagamentosService;
        this.planosService = planosService;
        this.usuariosService = usuariosService;
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

}
