package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateBigInteger;
import com.oficinadobrito.api.utils.GenerateUUID;
import com.oficinadobrito.api.utils.dtos.feedback.CreateFeedbackDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.math.BigInteger;

class FeedbacksEntityTests {
    private Feedback feedback;
    private String novoComentario;
    private String novoRemetenteUsername;
    private BigInteger idAleatorio;

    @BeforeEach
    void setUp() {
        feedback = new Feedback();
        novoComentario = "Ótimo serviço!";
        novoRemetenteUsername = "user123";
        idAleatorio = GenerateBigInteger.generateCustomRandom();
    }

    @DisplayName("Feedback must have a stars attribute")
    @Test
    void testFeedbackMustHaveStarsAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field stars = Feedback.class.getDeclaredField("stars");

        // When - Act
        feedback.setStars(4.5);

        // Then - Assert
        Assertions.assertNotNull(stars, () -> "The stars attribute does not exist in Feedback");
        Assertions.assertEquals(double.class, stars.getType(), () -> "The type of the stars attribute must be double");
        Assertions.assertEquals(4.5, feedback.getStars(), () -> "The stars value was not set correctly");
    }

    @DisplayName("Stars attribute must be between 0 and 5")
    @Test
    void testFeedbackStarsAttributeRange() {
        // Given - Arrange
        double validStars = 4.0;
        double invalidStarsLow = -1.0;
        double invalidStarsHigh = 6.0;

        // When - Act
        feedback.setStars(validStars);

        // Then - Assert
        Assertions.assertEquals(validStars, feedback.getStars(), () -> "Stars value is not correct");

        // Assert invalid values
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.setStars(invalidStarsLow));
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.setStars(invalidStarsHigh));
    }

    @DisplayName("Feedback must have a comentario attribute")
    @Test
    void testFeedbackMustHaveComentarioAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field comentario = Feedback.class.getDeclaredField("comentario");

        // When - Act
        feedback.setComentario(novoComentario);

        // Then - Assert
        Assertions.assertNotNull(comentario, () -> "The comentario attribute does not exist in Feedback");
        Assertions.assertEquals(String.class, comentario.getType(), () -> "The type of the comentario attribute must be String");
        Assertions.assertEquals(novoComentario, feedback.getComentario(), () -> "The comentario value was not set correctly");
    }

    @DisplayName("Feedback must have a remetenteUsername attribute")
    @Test
    void testFeedbackMustHaveRemetenteUsernameAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field remetenteUsername = Feedback.class.getDeclaredField("remetenteUsername");

        // When - Act
        feedback.setRemetenteUsername(novoRemetenteUsername);

        // Then - Assert
        Assertions.assertNotNull(remetenteUsername, () -> "The remetenteUsername attribute does not exist in Feedback");
        Assertions.assertEquals(String.class, remetenteUsername.getType(), () -> "The type of the remetenteUsername attribute must be String");
        Assertions.assertEquals(novoRemetenteUsername, feedback.getRemetenteUsername(), () -> "The remetenteUsername value was not set correctly");
    }

    @DisplayName("Feedback must have a usuario relationship")
    @Test
    void testFeedbackUsuarioRelationship() {
        // Given - Arrange
        Usuario usuario = new Usuario();
        usuario.setUsuarioId("123");

        // When - Act
        feedback.setUsuario(usuario);

        // Then - Assert
        Assertions.assertNotNull(feedback.getUsuario(), () -> "The usuario relationship is not correctly established");
        Assertions.assertEquals(usuario.getUsuarioId(), feedback.getUsuario().getUsuarioId(), () -> "The usuarioId is not set correctly");
    }

    @DisplayName("Feedback must have an anuncio relationship")
    @Test
    void testFeedbackAnuncioRelationship() {
        // Given - Arrange
        Anuncio anuncio = new Anuncio();
        anuncio.setAnuncioId(this.idAleatorio);

        // When - Act
        feedback.setAnuncio(anuncio);

        // Then - Assert
        Assertions.assertNotNull(feedback.getAnuncio(), () -> "The anuncio relationship is not correctly established");
        Assertions.assertEquals(anuncio.getAnuncioId(), feedback.getAnuncio().getAnuncioId(), () -> "The anuncioId is not set correctly");
    }

    @DisplayName("Create Feedback from DTO")
    @Test
    void testCreateFeedbackFromDto() {
        // Given - Arrange
        CreateFeedbackDto dto = new CreateFeedbackDto(4.5, "Ótimo serviço!","@netox" , GenerateUUID.generateUUID(),GenerateBigInteger.generateCustomRandom());

        // When - Act
        Feedback feedbackFromDto = Feedback.createDtoToEntity(dto);

        // Then - Assert
        Assertions.assertEquals(dto.remetenteUsername(), feedbackFromDto.getRemetenteUsername(), "The remetenteUsername was not correctly set from DTO");
        Assertions.assertEquals(dto.stars(), feedbackFromDto.getStars(), "The stars value was not correctly set from DTO");
        Assertions.assertEquals(dto.comentario(), feedbackFromDto.getComentario(), "The comentario was not correctly set from DTO");
    }
}
