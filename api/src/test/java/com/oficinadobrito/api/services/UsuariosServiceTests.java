package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.BadRequestException;
import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.repositories.UsuarioRepository;
import com.oficinadobrito.api.utils.dtos.usuario.LoginDto;
import com.oficinadobrito.api.utils.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest()
class UsuariosServiceTests {
    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CpfEncryptService cpfEncryptService;

    @InjectMocks
    private UsuariosService usuarioService;

    private Usuario user;

    @BeforeEach
    void setUp() {
        user = new Usuario("Jonas", "Jonas@gmail.com","84999832710","655.468.420-40","JonasGuty53#", UserRole.USER);
    }

    void mockIdSetter() {
        String uuid = UUID.randomUUID().toString();
        this.user.setUsuarioId(uuid);
    }

    @DisplayName("When create Usuario success")
    @Test
    void testUsuarioCreateWhenSuccessReturnUsuarioObject() {
        //Given - Arrange / When - Act
        mockIdSetter();
        when(this.usuarioService.newUsuario(user)).thenReturn(user); // mock method
        var actual = this.usuarioService.newUsuario(user);

        //Then  - Assert
        assertNotNull(actual, () -> "The user is successfully created, it is not being returned");
        assertEquals(this.user.getUsuarioId().toUpperCase(),actual.getUsuarioId().toUpperCase(), () -> "The user created successfully does not have an ID");
    }

    @DisplayName("when the user is created wrongly")
    @Test
    void testUsuarioCreateWhenFailReturnUsuarioObject() {
        //Given - Arrange / When - Act
        user.setEmail(" ");
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {this.usuarioService.newUsuario(user);});
        assertNotNull(thrown,()->"The section is not being released");
        assertEquals("The email provided is in the wrong format or a user with that email is already in this application", thrown.getMessage());
    }

    @DisplayName("Test creating user successfully")
    @Test
    void testCreateUserSuccess() {
        when(usuarioRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(user)).thenReturn(user);

        Usuario result = usuarioService.newUsuario(user);

        assertNotNull(result, "User should not be null");
        assertEquals(user.getEmail(), result.getEmail(), "User email should match");
    }

    @DisplayName("When creating user with invalid email format")
    @Test
    void testCreateUserInvalidEmail() {
        user.setEmail("invalid-email");

        BadRequestException thrown = assertThrows(BadRequestException.class, () -> usuarioService.newUsuario(this.user));
        assertEquals("The email provided is in the wrong format or a user with that email is already in this application", thrown.getMessage());
    }

    @DisplayName("When user authentication success")
    @Test
    void testAuthenticateUserSuccess() {
        LoginDto loginDto = new LoginDto("jonas@gmail.com", "JonasGuty53#");
        Authentication authentication = mock(Authentication.class);
        Usuario usuario = new Usuario();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(jwtService.generateToken(usuario)).thenReturn("mock-jwt-token");

        String token = usuarioService.authenticarUsuario(loginDto);

        assertNotNull(token, "Token should not be null");
        assertEquals("mock-jwt-token", token, "Token should match the mock value");
    }

    @DisplayName("Test user authentication with invalid credentials")
    @Test
    void testAuthenticateUserInvalidCredentials() {
        LoginDto loginDto = new LoginDto("jonas@gmail.com", "invalid-password");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        String token = usuarioService.authenticarUsuario(loginDto);

        assertEquals("", token, "Token should be empty for invalid credentials");
    }

}
