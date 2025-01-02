package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.GenerateBigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.math.BigInteger;

@ActiveProfiles("test")
@SpringBootTest
public class CategoriasEntityTests {
    private Categoria categoria;
    private String nome;
    private BigInteger bIAleatorio;

    @BeforeEach
    void setUp(){
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
        Field categoria = Categoria.class.getDeclaredField("categoria");

        //When  - Act
        this.categoria.setCategoria(this.nome);

        //Then  - Assert
        Assertions.assertNotNull(categoria, ()->"The name categoria attribute does not exist in user");
        Assertions.assertEquals(String.class,categoria.getType(), ()-> "the type of the user categoria attribute must be one String");
        Assertions.assertEquals(this.nome.toUpperCase(),this.categoria.getCategoria().toUpperCase(), () -> "Error in methods setCategoria or getCategoria");
    }

}
