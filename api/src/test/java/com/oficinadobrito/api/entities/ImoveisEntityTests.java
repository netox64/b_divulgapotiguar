package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateBigInteger;
import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class ImoveisEntityTests {
    private Imovel imovel;
    private String novoNome;
    private String novaLocalizacao;
    private String novaArea;
    private String novoTipo;
    private String novaDescricao;

    @BeforeEach
    void setUp() {
        imovel = new Imovel();
        novoNome = "Apartamento Luxo";
        novaLocalizacao = "Rua das Flores, 123";
        novaArea = "120m²";
        novoTipo = "Apartamento";
        novaDescricao = "Apartamento de 3 quartos com vista para o mar";
    }

    @DisplayName("Imovel must have a nome attribute")
    @Test
    void testImovelMustHaveNomeAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field nome = Imovel.class.getDeclaredField("nome");

        // When - Act
        imovel.setNome(novoNome);

        // Then - Assert
        assertNotNull(nome, "The nome attribute does not exist in Imovel");
        assertEquals(String.class, nome.getType(), "The type of the nome attribute must be String");
        assertEquals(novoNome, imovel.getNome(), "The nome value was not set correctly");
    }

    @DisplayName("Imovel nome attribute cannot be empty")
    @Test
    void testImovelNomeCannotBeEmpty() {
        // Given - Arrange
        String emptyNome = "";

        // When - Act & Then - Assert
        assertThrows(IllegalArgumentException.class, () -> {
            imovel.setNome(emptyNome);
        });
    }

    @DisplayName("Imovel must have a localizacao attribute")
    @Test
    void testImovelMustHaveLocalizacaoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field localizacao = Imovel.class.getDeclaredField("localizacao");

        // When - Act
        imovel.setLocalizacao(novaLocalizacao);

        // Then - Assert
        assertNotNull(localizacao, "The localizacao attribute does not exist in Imovel");
        assertEquals(String.class, localizacao.getType(), "The type of the localizacao attribute must be String");
        assertEquals(novaLocalizacao, imovel.getLocalizacao(), "The localizacao value was not set correctly");
    }

    @DisplayName("Imovel must have an area attribute")
    @Test
    void testImovelMustHaveAreaAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field area = Imovel.class.getDeclaredField("area");

        // When - Act
        imovel.setArea(novaArea);

        // Then - Assert
        assertNotNull(area, "The area attribute does not exist in Imovel");
        assertEquals(String.class, area.getType(), "The type of the area attribute must be String");
        assertEquals(novaArea, imovel.getArea(), "The area value was not set correctly");
    }

    @DisplayName("Imovel must have a tipo attribute")
    @Test
    void testImovelMustHaveTipoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field tipo = Imovel.class.getDeclaredField("tipo");

        // When - Act
        imovel.setTipo(novoTipo);

        // Then - Assert
        assertNotNull(tipo, "The tipo attribute does not exist in Imovel");
        assertEquals(String.class, tipo.getType(), "The type of the tipo attribute must be String");
        assertEquals(novoTipo, imovel.getTipo(), "The tipo value was not set correctly");
    }

    @DisplayName("Imovel must have a sobre attribute")
    @Test
    void testImovelMustHaveSobreAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field sobre = Imovel.class.getDeclaredField("sobre");

        // When - Act
        imovel.setSobre(novaDescricao);

        // Then - Assert
        assertNotNull(sobre, "The sobre attribute does not exist in Imovel");
        assertEquals(String.class, sobre.getType(), "The type of the sobre attribute must be String");
        assertEquals(novaDescricao, imovel.getSobre(), "The sobre value was not set correctly");
    }

    @DisplayName("Create Imovel from DTO")
    @Test
    void testCreateImovelFromDto() {
        // Given - Arrange
        CreateImovelDto dto = new CreateImovelDto(novoNome, novaLocalizacao, novaArea, novoTipo, novaDescricao);

        // When - Act
        Imovel imovelFromDto = Imovel.createDtoToEntity(dto);

        // Then - Assert
        assertEquals(dto.nome(), imovelFromDto.getNome(), "The nome was not correctly set from DTO");
        assertEquals(dto.localizacao(), imovelFromDto.getLocalizacao(), "The localizacao was not correctly set from DTO");
        assertEquals(dto.area(), imovelFromDto.getArea(), "The area was not correctly set from DTO");
        assertEquals(dto.tipo(), imovelFromDto.getTipo(), "The tipo was not correctly set from DTO");
        assertEquals(dto.sobre(), imovelFromDto.getSobre(), "The sobre was not correctly set from DTO");
    }

    @DisplayName("Update Imovel from DTO")
    @Test
    void testUpdateImovelFromDto() {
        // Given - Arrange
        UpdateImovelDto dto = new UpdateImovelDto(novoNome, novaArea, novoTipo, novaDescricao,GenerateBigInteger.generateCustomRandom());

        // When - Act
        Imovel updatedImovel = Imovel.updateDtoToEntity(dto);

        // Then - Assert
        assertEquals(dto.nome(), updatedImovel.getNome(), "The nome was not correctly updated from DTO");
        assertEquals(dto.area(), updatedImovel.getArea(), "The area was not correctly updated from DTO");
        assertEquals(dto.tipo(), updatedImovel.getTipo(), "The tipo was not correctly updated from DTO");
        assertEquals(dto.sobre(), updatedImovel.getSobre(), "The sobre was not correctly updated from DTO");
    }

    @DisplayName("Imovel should have a one-to-one relationship with Anuncio")
    @Test
    void testImovelAnuncioRelationship() {
        // Given - Arrange
        Anuncio anuncio = new Anuncio();
        anuncio.setAnuncioId(GenerateBigInteger.generateCustomRandom());
        imovel.setAnuncio(anuncio);

        // When - Act
        assertNotNull(imovel.getAnuncio(), "The anuncio relationship is not correctly established");
        assertEquals(anuncio.getAnuncioId(), imovel.getAnuncio().getAnuncioId(), "The anuncioId is not set correctly");
    }
}
