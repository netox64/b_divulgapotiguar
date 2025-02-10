package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.controllers.interfaces.IController;
import com.oficinadobrito.api.entities.Notificacao;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.NotificacoesService;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.notificacao.CreateNotificacaoDto;
import com.oficinadobrito.api.utils.dtos.notificacao.UpdateNotificacaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("notificacoes")
public class NotificacoesController implements IController<Notificacao, CreateNotificacaoDto, UpdateNotificacaoDto> {

    private final NotificacoesService notificacoesServices;
    private final UsuariosService usuariosService;

    public NotificacoesController(NotificacoesService notificacoesServices,UsuariosService usuariosService){
        this.notificacoesServices = notificacoesServices;
        this.usuariosService = usuariosService;
    }

    @Operation(summary = "post one notification -  (barer jwt) ->  role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @Override
    public ResponseEntity<Notificacao> postResource(@RequestBody @Valid CreateNotificacaoDto resource) {
        Notificacao nova = Notificacao.createDtoToEntity(resource);
        Usuario usuario = this.usuariosService.findUsuarioById(resource.usuarioId());
        nova.setUsuario(usuario);
        var notificacao = this.notificacoesServices.save(nova);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacao);
    }

    @Operation(summary = "get all notification -  (barer jwt) ->  role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Notificacao>> getAllResource() {
        List<Notificacao> notificacaoList = StreamSupport.stream(this.notificacoesServices.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(notificacaoList);
    }

    @Operation(summary = "get one notification -  (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Notificacao> getResourceById(@PathVariable("id") BigInteger id) {
        Notificacao notificacao = this.notificacoesServices.findById(id);
        return  ResponseEntity.ok(notificacao);
    }
    @Operation(summary = "update one notification -  (barer jwt) ->  role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Notificacao> updateResource(@PathVariable("id") BigInteger id,@RequestBody @Valid UpdateNotificacaoDto resource) {
        Notificacao anuncio = this.notificacoesServices.updateById(id,resource);
        return ResponseEntity.ok(anuncio);
    }

    @Operation(summary = "delete one notification -  (barer jwt) -> role USER or role ADMIN")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteResourceById(@PathVariable("id") BigInteger id) {
        this.notificacoesServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
