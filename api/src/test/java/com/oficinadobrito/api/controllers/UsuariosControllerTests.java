package com.oficinadobrito.api.controllers;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.*;
import com.oficinadobrito.api.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UsuariosControllerTests {
    @InjectMocks
    private UsuariosController usuariosController;

    @Mock
    private NotificacoesService notificacoesService;

    @Mock
    private FeedbacksService feedbacksService;

    @Mock
    private PagamentosService pagamentosService;

    @Mock
    private PlanosService planosService;

    @Mock
    private UsuariosService usuariosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Test for when the controller's get all users method is called it returns a list of objects and the http 200 method")
    @Test
    void testGetAllUsuarios() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosService.getAll()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuariosController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }

    @DisplayName("Test for when the controller's get one users method is called it returns a one object http 200 method")
    @Test
    void testGetOneUsuarioSuccess() {
        String id = "1";
        Usuario usuario = new Usuario();
        when(usuariosService.findUsuarioById(id)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuariosController.getOneUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @DisplayName("Tested if when searching for a non-existent user the controller throws the correct exception")
    @Test
    void testGetOneUsuario_NotFound() {
        String id = "1";
        when(usuariosService.findUsuarioById(id)).thenThrow(new ResourceNotFoundException("Usuario not found"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuariosController.getOneUsuario(id);
        });

        assertEquals("Usuario not found", exception.getMessage());
    }
}
