package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Imovel;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImovelRepository extends IGenericRepository<Imovel> {
}
