package com.oficinadobrito.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class CpfEncryptService {

    @Value("${spring.cpf.secret}")
    String secret;

    public String encode(String cpf) {
        String cpfWithSecret = cpf + secret;
        return Base64.getEncoder().encodeToString(cpfWithSecret.getBytes());
    }

    public String decode(String encryptedCpf) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedCpf);
        String decryptedCpfWithSecret = new String(decodedBytes);
        return decryptedCpfWithSecret.replace(secret, "");
    }

}
