package com.oficinadobrito.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest()
class CpfEncryptServiceTests {

    @Autowired
    private CpfEncryptService cpfEncryptService;

    private String cpf;

    @BeforeEach
    void setUp() {
        String secret = "secret123";
        cpfEncryptService = new CpfEncryptService();
        cpfEncryptService.secret = secret;
        this.cpf = "36015301040";
    }

    @DisplayName("when ordered to encrypt the cpf must be different from the previous one")
    @Test
    void testEncode() {
        //Given - Arrange
        // When - Act
        String encodedCpf = cpfEncryptService.encode(this.cpf);

        // Then  - Assert
        assertNotEquals(this.cpf, encodedCpf);
    }

    @DisplayName("When decrypted, the CPF must be the same as the original")
    @Test
    void testDecode() {
        //Given - Arrange
        // When - Act
        String encodedCpf = cpfEncryptService.encode(this.cpf);
        String decodedCpf = cpfEncryptService.decode(encodedCpf);

        // Then  - Assert
        assertEquals(decodedCpf,this.cpf,()->"the encoded cpf should be the same as the original passed in encryption");
    }

}
