package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateUUID;
import com.oficinadobrito.api.utils.dtos.pagamento.CreatePagamentoDto;
import com.oficinadobrito.api.utils.dtos.pagamento.UpdatePagamentoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigInteger;

public class PagamentosEntityTests {
    private Pagamento pagamento;
    private String novoTipo;
    private String novoCartao;
    private String novaChavePix;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        novoTipo = "Cartão";
        novoCartao = "1234567812345678";
        novaChavePix = "chave@pix";
    }

    @DisplayName("Pagamento must have a tipo attribute")
    @Test
    void testPagamentoMustHaveTipoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field tipo = Pagamento.class.getDeclaredField("tipo");

        // When - Act
        pagamento.setTipo(novoTipo);

        // Then - Assert
        Assertions.assertNotNull(tipo, () -> "The tipo attribute does not exist in Pagamento");
        Assertions.assertEquals(String.class, tipo.getType(), () -> "The type of the tipo attribute must be String");
        Assertions.assertEquals(novoTipo, pagamento.getTipo(), () -> "The tipo value was not set correctly");
    }

    @DisplayName("Pagamento must have a valor attribute")
    @Test
    void testPagamentoMustHaveValorAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field valor = Pagamento.class.getDeclaredField("valor");

        // When - Act
        pagamento.setValor(34.5);

        // Then - Assert
        Assertions.assertNotNull(valor, () -> "The valor attribute does not exist in Pagamento");
        Assertions.assertEquals(double.class, valor.getType(), () -> "The type of the valor attribute must be String");
        Assertions.assertEquals(34.5, pagamento.getValor(), () -> "The valor value was not set correctly");
    }

    @DisplayName("Pagamento must have a cartao attribute")
    @Test
    void testPagamentoMustHaveCartaoAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field cartao = Pagamento.class.getDeclaredField("cartao");

        // When - Act
        pagamento.setCartao(novoCartao);

        // Then - Assert
        Assertions.assertNotNull(cartao, () -> "The cartao attribute does not exist in Pagamento");
        Assertions.assertEquals(String.class, cartao.getType(), () -> "The type of the cartao attribute must be String");
        Assertions.assertEquals(novoCartao, pagamento.getCartao(), () -> "The cartao value was not set correctly");
    }

    @DisplayName("Pagamento must have a chavePix attribute")
    @Test
    void testPagamentoMustHaveChavePixAttribute() throws NoSuchFieldException {
        // Given - Arrange
        Field chavePix = Pagamento.class.getDeclaredField("chavePix");

        // When - Act
        pagamento.setChavePix(novaChavePix);

        // Then - Assert
        Assertions.assertNotNull(chavePix, () -> "The chavePix attribute does not exist in Pagamento");
        Assertions.assertEquals(String.class, chavePix.getType(), () -> "The type of the chavePix attribute must be String");
        Assertions.assertEquals(novaChavePix, pagamento.getChavePix(), () -> "The chavePix value was not set correctly");
    }

    @DisplayName("Pagamento must have a usuario relationship")
    @Test
    void testPagamentoUsuarioRelationship() {
        // Given - Arrange
        Usuario usuario = new Usuario();
        usuario.setUsuarioId("123");

        // When - Act
        pagamento.setUsuario(usuario);

        // Then - Assert
        Assertions.assertNotNull(pagamento.getUsuario(), () -> "The usuario relationship is not correctly established");
        Assertions.assertEquals(usuario.getUsuarioId(), pagamento.getUsuario().getUsuarioId(), () -> "The usuarioId is not set correctly");
    }

    @DisplayName("Create Pagamento from DTO")
    @Test
    void testCreatePagamentoFromDto() {
        // Given - Arrange
        BigInteger[] bigIntegers = {new BigInteger("1234567890"), new BigInteger("9876543210"), new BigInteger("7891234567")};
        CreatePagamentoDto dto = new CreatePagamentoDto("Cartão", "1234567812345678", "chave@pix",34.5, GenerateUUID.generateUUID(),bigIntegers);

        // When - Act
        Pagamento pagamentoFromDto = Pagamento.createDtoToEntity(dto);

        // Then - Assert
        Assertions.assertEquals(dto.tipo(), pagamentoFromDto.getTipo(), "The tipo was not correctly set from DTO");
        Assertions.assertEquals(dto.cartao(), pagamentoFromDto.getCartao(), "The cartao was not correctly set from DTO");
        Assertions.assertEquals(dto.chavePix(), pagamentoFromDto.getChavePix(), "The chavePix was not correctly set from DTO");
    }

    @DisplayName("Update Pagamento from DTO")
    @Test
    void testUpdatePagamentoFromDto() {
        // Given - Arrange
        BigInteger[] bigIntegers = {new BigInteger("1234567890"), new BigInteger("9876543210"), new BigInteger("7891234567")};
        UpdatePagamentoDto dto = new UpdatePagamentoDto( "PIX", null, "novaChave@pix", GenerateUUID.generateUUID(), bigIntegers );

        // When - Act
        Pagamento updatedPagamento = Pagamento.updateDtoToEntity(dto);

        // Then - Assert
        Assertions.assertEquals(dto.tipo(), updatedPagamento.getTipo(), "The tipo was not correctly updated from DTO");
        Assertions.assertNull(updatedPagamento.getCartao(), "The cartao should be null for Pix");
        Assertions.assertEquals(dto.chavePix(), updatedPagamento.getChavePix(), "The chavePix was not correctly updated from DTO");
    }

//    @DisplayName("Pagamento must generate a valid comprovante number")
//    @Test
//    void testPagamentoMustGenerateValidComprovante() {
//        // Given - Arrange
//        BigInteger numComprovante1 = pagamento.getNumComprovante();
//        BigInteger numComprovante2 = GenerateBigInteger.generateCustomRandom();
//
//        // Then - Assert
//        Assertions.assertNotNull(numComprovante1, "Comprovante number should not be null");
//        Assertions.assertNotEquals(numComprovante1, numComprovante2, "Comprovante numbers should be different");
//        Assertions.assertTrue(numComprovante1.bitLength() >= 32, "Comprovante number should have the correct length");
//    }
}
