package com.oficinadobrito.api.repositories;

import com.oficinadobrito.api.entities.Categoria;
import com.oficinadobrito.api.repositories.interfaces.IGenericRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Set;

@Repository
public interface CategoriaRepository extends IGenericRepository<Categoria> {
    Set<Categoria> findAllByCategoriaIdIn(Set<BigInteger> categoriaIds);
}
