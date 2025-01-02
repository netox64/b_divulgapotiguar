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

public class UsuariosControllerTests {
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Test for when the controller's get all users method is called it returns a list of objects and the http 200 method")
    @Test
    public void testGetAllUsuarios() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());
        when(usuariosService.getAll()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> response = usuariosController.getAllUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarios, response.getBody());
    }

    @DisplayName("Test for when the controller's get one users method is called it returns a one object http 200 method")
    @Test
    public void testGetOneUsuarioSuccess() {
        String id = "1";
        Usuario usuario = new Usuario();
        when(usuariosService.findUsuarioById(id)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuariosController.getOneUsuario(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @DisplayName("Tested if when searching for a non-existent user the controller throws the correct exception")
    @Test
    public void testGetOneUsuario_NotFound() {
        String id = "1";
        when(usuariosService.findUsuarioById(id)).thenThrow(new ResourceNotFoundException("Usuario not found"));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuariosController.getOneUsuario(id);
        });

        assertEquals("Usuario not found", exception.getMessage());
    }

//    @Test
//    public void testUpdateUsuario_Success() {
//        String id = "1";
//        UpdateUsuarioDto updateDto = new UpdateUsuarioDto(); // Preencha conforme necessário
//        Usuario usuarioUpdate = new Usuario(); // Preencha conforme necessário
//        when(usuariosService.updateUsuario(id, usuarioUpdate)).thenReturn(usuarioUpdate);
//
//        ResponseEntity<?> response = usuariosController.updateUsuario(id, updateDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(usuarioUpdate, response.getBody());
//    }
//
//    @Test
//    public void testGetAllNotificaoesForUsuarioId_Success() {
//        String id = "1";
//        Set<Notificacao> notificacoes = new HashSet<>(Arrays.asList(new Notificacao()));
//        when(notificacoesService.getAllNotificationForUsuario(id)).thenReturn(notificacoes);
//
//        ResponseEntity<?> response = usuariosController.getAllNotificaoesForUsuarioId(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(notificacoes, response.getBody());
//    }
//
//    @Test
//    public void testGetAllNotificaoesForUsuarioId_NotFound() {
//        String id = "1";
//        when(notificacoesService.getAllNotificationForUsuario(id)).thenReturn(new HashSet<>());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            usuariosController.getAllNotificaoesForUsuarioId(id);
//        });
//
//        assertEquals("Not exists usuario with id informed", exception.getMessage());
//    }
//
//    @Test
//    public void testGetAllFeedbacksForUsuarioId_Success() {
//        String id = "1";
//        Set<Feedback> feedbacks = new HashSet<>(Arrays.asList(new Feedback()));
//        when(feedbacksService.getAllFeedbacksForUsuario(id)).thenReturn(feedbacks);
//
//        ResponseEntity<?> response = usuariosController.getAllFeedbacksForUsuarioId(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(feedbacks, response.getBody());
//    }
//
//    @Test
//    public void testGetAllFeedbacksForUsuarioId_NotFound() {
//        String id = "1";
//        when(feedbacksService.getAllFeedbacksForUsuario(id)).thenReturn(new HashSet<>());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            usuariosController.getAllFeedbacksForUsuarioId(id);
//        });
//
//        assertEquals("Not exists usuario with id informed", exception.getMessage());
//    }
//
//    @Test
//    public void testGetAllPagamentosForUsuarioId_Success() {
//        String id = "1";
//        Set<Pagamento> pagamentos = new HashSet<>(Arrays.asList(new Pagamento()));
//        when(pagamentosService.getAllPagamentosForUsuario(id)).thenReturn(pagamentos);
//
//        ResponseEntity<?> response = usuariosController.getAllPagamentosForUsuarioId(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(pagamentos, response.getBody());
//    }
//
//    @Test
//    public void testGetAllPagamentosForUsuarioId_NotFound() {
//        String id = "1";
//        when(pagamentosService.getAllPagamentosForUsuario(id)).thenReturn(new HashSet<>());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            usuariosController.getAllPagamentosForUsuarioId(id);
//        });
//
//        assertEquals("Not exists usuario with id informed", exception.getMessage());
//    }
//
//    @Test
//    public void testGetAllPlanosForUsuarioId_Success() {
//        String id = "1";
//        Set<Plano> planos = new HashSet<>(Arrays.asList(new Plano()));
//        when(planosService.getAllPlanosForUsuario(id)).thenReturn(planos);
//
//        ResponseEntity<?> response = usuariosController.getAllPlanosForUsuarioId(id);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(planos, response.getBody());
//    }
//
//    @Test
//    public void testGetAllPlanosForUsuarioId_NotFound() {
//        String id = "1";
//        when(planosService.getAllPlanosForUsuario(id)).thenReturn(new HashSet<>());
//
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            usuariosController.getAllPlanosForUsuarioId(id);
//        });
//
//        assertEquals("Not exists usuario with id informed", exception.getMessage());
//    }
}
