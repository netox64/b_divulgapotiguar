package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.ProcessDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TarefaRepository extends JpaRepository<ProcessDataInfo, Long> {
    Optional<ProcessDataInfo> findFirstByImovelIdIsNotNull();
}
