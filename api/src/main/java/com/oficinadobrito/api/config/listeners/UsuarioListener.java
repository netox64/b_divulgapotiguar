package com.oficinadobrito.api.config.listeners;

import com.oficinadobrito.api.entities.Usuario;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Component
public class UsuarioListener {
    private final PasswordEncoder passwordEncoder;

    public UsuarioListener(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @PrePersist
    @PreUpdate
    public void encryptPassword(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(LocalDate.now());
        }
    }
}
