package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);
    @Query("select u from Usuario u where u.email = :email")
    Optional<Usuario> findUsuarioByEmail(@Param("email") String email);
}
