package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateBigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.math.BigInteger;

class CategoriasEntityTests {
    private Categoria categoria;
    private String nome;
    private BigInteger bIAleatorio;

    @BeforeEach
    public void setUp(){
        this.categoria = new Categoria();
        this.nome = "Maiores";
        this.bIAleatorio = GenerateBigInteger.generateCustomRandom();
    }

    @DisplayName("When a categoria is created, the categoriaId attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheIdAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field id = Categoria.class.getDeclaredField("categoriaId");

        //When  - Act
        this.categoria.setCategoriaId(this.bIAleatorio);

        //Then  - Assert
        Assertions.assertNotNull(id, ()->"The id attribute does not exist in user");
        Assertions.assertEquals(BigInteger.class,id.getType(), ()-> "The type of the user ID attribute must be one BigInteger");
        Assertions.assertEquals(this.bIAleatorio,this.categoria.getCategoriaId(), () -> "Error in methods setId or getId");
    }

    @DisplayName("When a user is created, the categoria attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheUsernameAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field nomeField = Categoria.class.getDeclaredField("nome");

        //When  - Act
        this.categoria.setNome(this.nome);

        //Then  - Assert
        Assertions.assertNotNull(nomeField, ()->"The name categoria attribute does not exist in user");
        Assertions.assertEquals(String.class, nomeField.getType(), ()-> "the type of the name attribute must be one String");
        Assertions.assertEquals(this.nome.toUpperCase(), this.categoria.getNome().toUpperCase(), () -> "Error in methods setNOme or getNome for Categoria");
    }

}
