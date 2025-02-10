package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Pagamento;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends IGenericRepository<Pagamento> {

    @Query("SELECT f FROM Pagamento f WHERE f.usuario.usuarioId = :usuarioId")
    List<Pagamento> findByUsuarioId(@Param("usuarioId") String usuarioId);
}
