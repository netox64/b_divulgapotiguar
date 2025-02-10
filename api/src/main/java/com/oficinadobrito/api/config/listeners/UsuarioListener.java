package com.oficinadobrito.api.config.listeners;

import com.oficinadobrito.api.entities.Usuario;
import com.oficinadobrito.api.services.CpfEncryptService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class UsuarioListener {
    
    private final PasswordEncoder passwordEncoder;
    private final CpfEncryptService cpfEncoder;

    public UsuarioListener(PasswordEncoder passwordEncoder,CpfEncryptService cpfEncoder){
        this.passwordEncoder = passwordEncoder;
        this.cpfEncoder = cpfEncoder;
    }

    @PrePersist
    public void encryptPasswordAndCpf(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        }

        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(LocalDate.now());
        }

        if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()) {
            String encryptedCpf = this.cpfEncoder.encode(usuario.getCpf());
            usuario.setCpf(encryptedCpf);
        }
    }

    @PreUpdate
    public void encryptPasswordInUpdate(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        }
    }
}
