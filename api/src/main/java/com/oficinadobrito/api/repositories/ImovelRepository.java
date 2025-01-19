package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends IGenericRepository<Imovel> {

    @Query("SELECT f FROM Imovel f WHERE f.usuario.usuarioId = :usuarioId")
    List<Imovel> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
