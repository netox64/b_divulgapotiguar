package com.oficinadobrito.api.repositories;
import com.oficinadobrito.api.entities.Notificacao;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends IGenericRepository<Notificacao> {

    @Query("SELECT f FROM Notificacao f WHERE f.usuario.usuarioId = :usuarioId")
    List<Notificacao> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
