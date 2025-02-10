package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationsController {

    private final UsuariosService usuariosService;

    public AuthenticationsController(UsuariosService usuariosService ) {
        this.usuariosService = usuariosService;
    }

    @Operation(summary = "Authenticates a user in the application")
    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> autenticar(@RequestBody @Valid LoginDto usuario) {
        var token = this.usuariosService.authenticarUsuario(usuario);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse("User with credentials not found or invalid"));
        }
        TokenResponse tokenResponse = new TokenResponse(token);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @Operation(summary = "Register a user in the application")
    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody @Valid CreateUserDto userDto) {
        Usuario user = Usuario.toEntity(userDto);
        Usuario usuario =  this.usuariosService.newUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Operation(summary = "Send an email with specific content to a user")
    @PermitAll
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmailReset( @Valid @RequestBody EmailDto email) {
        boolean enviou = this.usuariosService.sendHash(email.email());
        return enviou? ResponseEntity.ok().body("Enviado"): ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Resets a user's password, if the reset code is valid")
    @PermitAll
    @PutMapping("/redefinir")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedemPasswordDto rePasswordDto) {
        VerifyTokenPassword verificado = this.usuariosService.verifyHash(rePasswordDto.token());
        if(verificado.isVerificado()){
            boolean redefinido = this.usuariosService.redefinirSenha(verificado.getEmail(),rePasswordDto.password());
            return ResponseEntity.ok().body("sucesso -> redefinido password: " + redefinido);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
