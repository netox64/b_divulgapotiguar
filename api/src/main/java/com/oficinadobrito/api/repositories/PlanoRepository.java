package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Plano;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoRepository extends IGenericRepository<Plano> {

    @Query("SELECT f FROM Plano f WHERE f.usuario.usuarioId = :usuarioId")
    List<Plano> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
