package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateBigInteger;
import com.oficinadobrito.api.utils.GenerateUUID;
import com.oficinadobrito.api.utils.dtos.plano.CreatePlanoDto;
import com.oficinadobrito.api.utils.dtos.plano.UpdatePlanoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PlanosEntityTests {
    private Plano plano;
    private double valorPlano;
    private int quantAnuncio;
    private LocalDate dataAdquerido;
    private Usuario usuario;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        plano = new Plano();
        valorPlano = 150.0;
        quantAnuncio = 50;
        dataAdquerido = LocalDate.now().minusDays(1); // Data no passado
        usuario = new Usuario(); // Simula um usuário
        pagamento = new Pagamento(); // Simula um pagamento
    }

    @DisplayName("Plano must have a valor attribute")
    @Test
    void testPlanoMustHaveValorAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field valor = Plano.class.getDeclaredField("valor");

        // When - Act
        plano.setValor(valorPlano);

        // Then - Assert
        assertNotNull(valor, "The valor attribute does not exist in Plano");
        assertEquals(double.class, valor.getType(), "The type of the valor attribute must be double");
        assertEquals(valorPlano, plano.getValor(), "The valor value was not set correctly");
    }

    @DisplayName("Plano valor attribute must be greater than or equal to 0")
    @Test
    void testPlanoValorMustBeGreaterThanZero() {
        // Given - Arrange
        double valorNegativo = -1.0;

        // When - Act & Then - Assert
        assertThrows(IllegalArgumentException.class, () -> plano.setValor(valorNegativo), "The valor must be greater than or equal to 0");
    }

    @DisplayName("Plano must have a quantAnuncio attribute")
    @Test
    void testPlanoMustHaveQuantAnuncioAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field quantAnuncioField = Plano.class.getDeclaredField("quantAnuncio");

        // When - Act
        plano.setQuantAnuncio(quantAnuncio);

        // Then - Assert
        assertNotNull(quantAnuncioField, "The quantAnuncio attribute does not exist in Plano");
        assertEquals(int.class, quantAnuncioField.getType(), "The type of the quantAnuncio attribute must be int");
        assertEquals(quantAnuncio, plano.getQuantAnuncio(), "The quantAnuncio value was not set correctly");
    }

    @DisplayName("Plano quantAnuncio must be within valid range (0-120)")
    @Test
    void testPlanoQuantAnuncioMustBeInValidRange() {
        // Given - Arrange
        int quantAnuncioLow = -5;
        int quantAnuncioHigh = 130;

        // When - Act & Then - Assert
        assertThrows(IllegalArgumentException.class, () -> plano.setQuantAnuncio(quantAnuncioLow), "QuantAnuncio must be at least 0");
        assertThrows(IllegalArgumentException.class, () -> plano.setQuantAnuncio(quantAnuncioHigh), "QuantAnuncio must be at most 50");
    }

    @DisplayName("Plano must have a dataAdquerido attribute")
    @Test
    void testPlanoMustHaveDataAdqueridoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field dataAdqueridoField = Plano.class.getDeclaredField("dataAdquerido");

        // When - Act
        plano.setDataAdquerido(dataAdquerido);

        // Then - Assert
        assertNotNull(dataAdqueridoField, "The dataAdquerido attribute does not exist in Plano");
        assertEquals(LocalDate.class, dataAdqueridoField.getType(), "The type of the dataAdquerido attribute must be LocalDate");
        assertEquals(dataAdquerido, plano.getDataAdquerido(), "The dataAdquerido value was not set correctly");
    }

    @DisplayName("Plano must have a relacionamento com Pagamento")
    @Test
    void testPlanoPagamentoRelationship() {
        // Given - Arrange
        plano.setPagamento(pagamento);

        // When - Act & Then - Assert
        assertNotNull(plano.getPagamento(), "The pagamento relationship is not correctly established");
    }

    @DisplayName("Plano must have a relacionamento com Usuario")
    @Test
    void testPlanoUsuarioRelationship() {
        // Given - Arrange
        plano.setUsuario(usuario);

        // When - Act & Then - Assert
        assertNotNull(plano.getUsuario(), "The usuario relationship is not correctly established");
    }

    @DisplayName("Create Plano from DTO")
    @Test
    void testCreatePlanoFromDto() {
        // Given - Arrange
        CreatePlanoDto dto = new CreatePlanoDto(valorPlano, quantAnuncio, dataAdquerido, GenerateBigInteger.generateCustomRandom(),GenerateUUID.generateUUID());

        // When - Act
        Plano planoFromDto = Plano.createDtoToEntity(dto);

        // Then - Assert
        assertEquals(this.valorPlano, planoFromDto.getValor(), "The valor was not correctly set from DTO");
        assertEquals(this.quantAnuncio, planoFromDto.getQuantAnuncio(), "The quantAnuncio was not correctly set from DTO");
        assertEquals(this.dataAdquerido, planoFromDto.getDataAdquerido(), "The dataAdquerido was not correctly set from DTO");
    }

    @DisplayName("Update Plano from DTO")
    @Test
    void testUpdatePlanoFromDto() {
        // Given - Arrange
        UpdatePlanoDto dto = new UpdatePlanoDto(150.0, 50, GenerateUUID.generateUUID());

        // When - Act
        Plano planoFromDto = Plano.updateDtoToEntity(dto);

        // Then - Assert
        assertEquals(this.valorPlano, planoFromDto.getValor(), "The valor was not correctly updated from DTO");
        assertEquals(this.quantAnuncio, planoFromDto.getQuantAnuncio(), "The quantAnuncio was not correctly updated from DTO");
    }

    @DisplayName("Plano should have a many-to-many relationship with Anuncio")
    @Test
    void testPlanoAnuncioRelationship() {
        // Given - Arrange
        Anuncio anuncio = new Anuncio();
        plano.getAnuncios().add(anuncio);

        // When - Act & Then - Assert
        assertTrue(plano.getAnuncios().contains(anuncio), "The anuncio relationship is not correctly established");
    }
}