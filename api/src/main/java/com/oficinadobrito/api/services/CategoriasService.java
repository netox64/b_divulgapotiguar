package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Categoria;
import com.oficinadobrito.api.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Set;

@Service
public class CategoriasService extends GenericService<Categoria> {
    private final CategoriaRepository categoriaRepository;

    public CategoriasService(CategoriaRepository repository,CategoriaRepository categoriaRepository) {
        super(repository);
        this.categoriaRepository= categoriaRepository;
    }

    public Set<Categoria> getAllCategoriaWithIds(Set<BigInteger> ids){
        return (Set<Categoria>) this.categoriaRepository.findAllByCategoriaIdIn(ids);
    }
}
