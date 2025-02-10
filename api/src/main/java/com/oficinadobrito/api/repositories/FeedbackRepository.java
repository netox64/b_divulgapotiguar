package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Feedback;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends IGenericRepository<Feedback> {

    @Query("SELECT f FROM Feedback f WHERE f.usuario.usuarioId = :usuarioId")
    List<Feedback> findByUsuarioId(@Param("usuarioId") String usuarioId);

}
