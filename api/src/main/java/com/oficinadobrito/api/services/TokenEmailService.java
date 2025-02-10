package com.oficinadobrito.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class TokenEmailService {

    @Value("${spring.security.token.secret}")
    private String secretKey;

    private static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;

    private SecretKey getSecretKey() {
        byte[] keyBytes = new byte[32]; // 256 bits = 32 bytes
        byte[] providedKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(providedKeyBytes, 0, keyBytes, 0, Math.min(providedKeyBytes.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String email) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

        String tokenData = email + ":" + secretKey;
        byte[] encrypted = cipher.doFinal(tokenData.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedIVAndText = new byte[IV_LENGTH_BYTE + encrypted.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, IV_LENGTH_BYTE);
        System.arraycopy(encrypted, 0, encryptedIVAndText, IV_LENGTH_BYTE, encrypted.length);
        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }

    public String decrypt(String token) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        byte[] decode = Base64.getDecoder().decode(token);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, decode, 0, IV_LENGTH_BYTE);
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        byte[] decryptedText = cipher.doFinal(decode, IV_LENGTH_BYTE, decode.length - IV_LENGTH_BYTE);
        return new String(decryptedText, StandardCharsets.UTF_8);
    }

    public boolean validateToken(String token, String email) {
        try {
            String decryptedData = decrypt(token);
            String[] parts = decryptedData.split(":");
            return parts[0].equals(email) && parts[1].equals(secretKey);
        } catch (Exception e) {
            return false;
        }
    }
}
