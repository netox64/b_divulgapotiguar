package com.oficinadobrito.api.services;

import com.oficinadobrito.api.entities.Categoria;
import com.oficinadobrito.api.repositories.CategoriaRepository;
import com.oficinadobrito.api.utils.dtos.categoria.UpdateCategoriaDto;
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
        return this.categoriaRepository.findAllByCategoriaIdIn(ids);
    }
    public Categoria updateById(BigInteger categoriaId, UpdateCategoriaDto updateCategoriaDto) {
        Categoria categoriaAtualizar = this.findById(categoriaId);

        if (updateCategoriaDto.nome() != null && !updateCategoriaDto.nome().isEmpty()) {
            categoriaAtualizar.setNome(updateCategoriaDto.nome());
        }
        if (updateCategoriaDto.descricao() != null && !updateCategoriaDto.descricao().isEmpty()) {
            categoriaAtualizar.setDescricao(updateCategoriaDto.descricao());
        }

        return this.save(categoriaAtualizar);
    }
}
