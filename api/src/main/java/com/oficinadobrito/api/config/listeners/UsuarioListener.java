package com.oficinadobrito.api.config.listeners;

import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.PasswordEncryptor;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class UsuarioListener {
    
    private static PasswordEncryptor passwordEncryptor;

    public static void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        UsuarioListener.passwordEncryptor = passwordEncryptor;
    }

    @PrePersist
    @PreUpdate
    public void encryptPassword(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncryptor.encode(usuario.getPassword()));
        }

        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(LocalDate.now());
        }
    }
}
