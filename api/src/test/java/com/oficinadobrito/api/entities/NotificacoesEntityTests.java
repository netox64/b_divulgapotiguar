package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateUUID;
import com.oficinadobrito.api.utils.dtos.notificacao.CreateNotificacaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class NotificacoesEntityTests {
    private Notificacao notificacao;
    private String novoAssunto;
    private String novaDescricao;
    private String novoStatus;

    @BeforeEach
    void setUp() {
        notificacao = new Notificacao();
        novoAssunto = "Novo Alerta";
        novaDescricao = "Este é um aviso de teste";
        novoStatus = "ENVIADO";
    }

    @DisplayName("Notificacao must have an assunto attribute")
    @Test
    void testNotificacaoMustHaveAssuntoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field assunto = Notificacao.class.getDeclaredField("assunto");

        // When - Act
        notificacao.setAssunto(novoAssunto);

        // Then - Assert
        assertNotNull(assunto, "The assunto attribute does not exist in Notificacao");
        assertEquals(String.class, assunto.getType(), "The type of the assunto attribute must be String");
        assertEquals(novoAssunto, notificacao.getAssunto(), "The assunto value was not set correctly");
    }

    @DisplayName("Notificacao assunto attribute must not be empty or null")
    @Test
    void testNotificacaoAssuntoCannotBeEmptyOrNull() {
        // Given - Arrange
        String emptyAssunto = "";

        // When - Act & Then - Assert
        assertThrows(IllegalArgumentException.class, () -> {notificacao.setAssunto(emptyAssunto);});
    }

    @DisplayName("Notificacao must have a descricao attribute")
    @Test
    void testNotificacaoMustHaveDescricaoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field descricao = Notificacao.class.getDeclaredField("descricao");

        // When - Act
        notificacao.setDescricao(novaDescricao);

        // Then - Assert
        assertNotNull(descricao, "The descricao attribute does not exist in Notificacao");
        assertEquals(String.class, descricao.getType(), "The type of the descricao attribute must be String");
        assertEquals(novaDescricao, notificacao.getDescricao(), "The descricao value was not set correctly");
    }

    @DisplayName("Notificacao must have a status attribute")
    @Test
    void testNotificacaoMustHaveStatusAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field status = Notificacao.class.getDeclaredField("status");

        // When - Act
        notificacao.setStatus(novoStatus);

        // Then - Assert
        assertNotNull(status, "The status attribute does not exist in Notificacao");
        assertEquals(String.class, status.getType(), "The type of the status attribute must be String");
        assertEquals(novoStatus, notificacao.getStatus(), "The status value was not set correctly");
    }

    @DisplayName("Create Notificacao from DTO")
    @Test
    void testCreateNotificacaoFromDto() {
        // Given - Arrange
        CreateNotificacaoDto dto = new CreateNotificacaoDto(GenerateUUID.generateUUID(),novoAssunto, novaDescricao, novoStatus);

        // When - Act
        Notificacao notificacaoFromDto = Notificacao.createDtoToEntity(dto);

        // Then - Assert
        assertEquals(dto.assunto(), notificacaoFromDto.getAssunto(), "The assunto was not correctly set from DTO");
        assertEquals(dto.descricao(), notificacaoFromDto.getDescricao(), "The descricao was not correctly set from DTO");
        assertEquals(dto.status(), notificacaoFromDto.getStatus(), "The status was not correctly set from DTO");
    }

    @DisplayName("Notificacao should have a many-to-one relationship with Usuario")
    @Test
    void testNotificacaoUsuarioRelationship() {
        // Given - Arrange
        Usuario usuario = new Usuario(); // Simula um usuário
        usuario.setUsuarioId(GenerateUUID.generateUUID());
        notificacao.setUsuario(usuario);

        // When - Act
        assertNotNull(notificacao.getUsuario(), "The usuario relationship is not correctly established");
        assertEquals(usuario.getUsuarioId(), notificacao.getUsuario().getUsuarioId(), "The usuarioId is not set correctly");
    }
}
