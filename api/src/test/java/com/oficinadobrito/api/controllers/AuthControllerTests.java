package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.UsuariosService;
import com.oficinadobrito.api.utils.dtos.usuario.*;
import com.oficinadobrito.api.utils.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AuthControllerTests {

    @InjectMocks
    private AuthenticationsController authenticationsController;

    @Mock
    private UsuariosService usuariosService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.usuario = new Usuario();
        this.usuario.setUsername("user");
        this.usuario.setEmail("user@gmail.com");
        this.usuario.setPassword("Password@3000");
        this.usuario.setRole(UserRole.USER);
    }

    @DisplayName("When the user logs in correctly to the application")
    @Test
    void testAutenticarUserFound() {
        LoginDto loginDto = new LoginDto("user@gmail.com", "password");
        String token = "some-token";
        when(usuariosService.authenticarUsuario(loginDto)).thenReturn(token);

        ResponseEntity<TokenResponse> response = authenticationsController.autenticar(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody().token());
    }

    @DisplayName("When login is not successful, the user does not exist")
    @Test
    void testAutenticarUserNotFound() {
        LoginDto loginDto = new LoginDto("user", "wrongpassword");
        when(usuariosService.authenticarUsuario(loginDto)).thenReturn("");

        ResponseEntity<TokenResponse> response = authenticationsController.autenticar(loginDto);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User with credentials not found or invalid", response.getBody().token());
    }

    @DisplayName("When the user is successfully registered")
    @Test
    void testRegisterSuccess() {
        CreateUserDto createUserDto = new CreateUserDto("user", "user@gmail.com" ,"Password@3000");

        when(this.usuariosService.newUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = authenticationsController.register(createUserDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @DisplayName("When the user is failure registered")
    @Test
    void testRegisterFailure() {
        CreateUserDto createUserDto = new CreateUserDto("user", "user@gmail.com" ,"Password@3000");
        when(usuariosService.newUsuario(any(Usuario.class))).thenThrow(new BadRequestException("The email provided is in the wrong format or a user with that email is already in this application"));

        Exception exception = assertThrows(BadRequestException.class, () -> {authenticationsController.register(createUserDto);});

        assertEquals("The email provided is in the wrong format or a user with that email is already in this application", exception.getMessage());
    }

    @DisplayName("when the password reset email is sent")
    @Test
    void testSendEmailResetSuccess() {
        EmailDto email = new EmailDto("user@example.com");
        when(usuariosService.sendHash(email.email())).thenReturn(true);

        ResponseEntity<String> response = authenticationsController.sendEmailReset(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Enviado", response.getBody());
    }

    @DisplayName("when the password reset email is sent and email notfound")
    @Test
    void testSendEmailResetNotFound() {
        EmailDto email = new EmailDto("user@example.com");
        when(usuariosService.sendHash(email.email())).thenReturn(false);

        ResponseEntity<String> response = authenticationsController.sendEmailReset(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}