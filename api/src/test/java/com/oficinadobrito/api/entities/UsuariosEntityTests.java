package com.oficinadobrito.api.entities;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;

import com.oficinadobrito.api.utils.enums.UserRole;

class UsuariosEntityTests {
    private Usuario usuario;
    private String novo;

    @BeforeEach
    void setUp(){
        usuario = new Usuario();
        usuario.setUsuarioId(UUID.randomUUID().toString()); 
        this.novo = "Caleby";
    }

    @DisplayName("User must be an implementation and UserDetails")
    @Test
    void testUserMustBeAnImplementationAndUserDetail() {
        //Given - Arrange / When  - Act
        //Then  - Assert
        Assertions.assertTrue(this.usuario instanceof UserDetails, ()-> "User is not implementing userDetail, as it should");
    }

    @DisplayName("When a user is created, the usuarioId attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheIdAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        String novoId = UUID.randomUUID().toString();
        Field id = Usuario.class.getDeclaredField("usuarioId");

        //When  - Act
        this.usuario.setUsuarioId(novoId);

        //Then  - Assert
        Assertions.assertNotNull(id, ()->"The id attribute does not exist in user");
        Assertions.assertEquals(String.class,id.getType(), ()-> "The type of the user ID attribute must be one UUID");
        Assertions.assertEquals(novoId,this.usuario.getUsuarioId(), () -> "Error in methods setId or getId");
    }

    @DisplayName("When a user is created, the username attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheUsernameAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field username = Usuario.class.getDeclaredField("username");

        //When  - Act
        this.usuario.setUsername(this.novo);

        //Then  - Assert
        Assertions.assertNotNull(username, ()->"The username attribute does not exist in user");
        Assertions.assertEquals(String.class,username.getType(), ()-> "the type of the user username attribute must be one String");
        Assertions.assertEquals(novo.toUpperCase(),this.usuario.getUsername().toUpperCase(), () -> "Error in methods setUsername or getUsername");
    }

    @DisplayName("When a user is created, the phone attribute must exist")
    @Test
    void testWhenAUserIsCreatedThePhoneAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field phone = Usuario.class.getDeclaredField("phone");

        //When  - Act
        this.usuario.setPhone("86999832710");

        //Then  - Assert
        Assertions.assertNotNull(phone, ()->"The phone number attribute does not exist in user");
        Assertions.assertEquals(String.class,phone.getType(), ()-> "the type of the user phone attribute must be one String");
        Assertions.assertEquals("86999832710".toUpperCase(),this.usuario.getPhone().toUpperCase(), () -> "Error in methods setPhone or getPhone");
    }

    @DisplayName("When a user is created, the cpf attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheCpfAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field cpf = Usuario.class.getDeclaredField("cpf");

        //When  - Act
        this.usuario.setPhone("655.468.420-40");

        //Then  - Assert
        Assertions.assertNotNull(cpf, ()->"The cpf number attribute does not exist in user");
        Assertions.assertEquals(String.class,cpf.getType(), ()-> "the type of the user cpf attribute must be one String");
        Assertions.assertEquals("655.468.420-40".toUpperCase(),this.usuario.getPhone().toUpperCase(), () -> "Error in methods setCpf or getCpf");
    }

    @DisplayName("When a user is created, the email attribute must exist")
    @Test
    void testWhenAUserIsCreatedTheEmailAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        String emailFake = "Caleby@gmail.com";
        Field email = Usuario.class.getDeclaredField("email");

        //When  - Act
        this.usuario.setEmail(emailFake);

        //Then  - Assert
        Assertions.assertNotNull(email, ()->"The email attribute does not exist in user");
        Assertions.assertEquals(String.class,email.getType(), ()-> "the type of the user email attribute must be one String");
        Assertions.assertEquals(emailFake.toUpperCase(),this.usuario.getEmail().toUpperCase(), () -> "Error in methods setEmail or getEMail");
    }

    @DisplayName("When a user is created, the password attribute must exist")
    @Test
    void testWhenAUserIsCreatedThePasswordAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field password = Usuario.class.getDeclaredField("password");
        String passwordString = "JordiAlph3000&343";

        //When  - Act
        this.usuario.setPassword(passwordString);

        //Then  - Assert
        Assertions.assertNotNull(password, ()->"The password attribute does not exist in user");
        Assertions.assertEquals(String.class,password.getType(), ()-> "the type of the user password attribute must be one String");
        Assertions.assertEquals(passwordString.toUpperCase(),this.usuario.getPassword().toUpperCase(), () -> "Error in methods setPassword or getPassword");
    }

    @DisplayName("When a user is created, the role attribute must exist and can be defined with the types, Admin, user, vendedor e gerente")
    @Test
    void testWhenAUserIsCreatedTheRoleAttributeMustExist() throws NoSuchFieldException {
        //Given - Arrange
        Field role = Usuario.class.getDeclaredField("role");

        //Then  - Assert
        Assertions.assertEquals(4, UserRole.values().length, ()-> "error the total number of values the UserRole enumeration must be 4: ADMIN, USER, VENDEDOR, GERENTE");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("ADMIN"), () -> "UserRole does not contain ADMIN");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("USER"), () -> "UserRole does not contain USER");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("VENDEDOR"),() -> "UserRole does not contain VENDEDOR");
        Assertions.assertDoesNotThrow(()->UserRole.valueOf("GERENTE"), () -> "UserRole does not contain GERENTE");
        Assertions.assertNotNull(role, ()-> "The role attribute does not exist in user");
        Assertions.assertEquals(UserRole.class, role.getType(), ()-> "The type of the user role attribute must be one UserRole");
    }

}
