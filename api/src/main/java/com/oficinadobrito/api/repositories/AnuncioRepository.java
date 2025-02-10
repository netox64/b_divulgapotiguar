package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Anuncio;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository  extends IGenericRepository<Anuncio> {

    @Query("SELECT f FROM Anuncio f WHERE f.usuario.usuarioId = :usuarioId")
    List<Anuncio> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
