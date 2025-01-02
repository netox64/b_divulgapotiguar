package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.categoria.CreateCategoriaDto;
import com.oficinadobrito.api.utils.dtos.categoria.UpdateCategoriaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_categorias")
public class Categoria {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger categoriaId;

    @Column(length = 100)
    @NotEmpty(message = "property 'categoria name' cannot be null, is required a value")
    private String categoria;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private Set<Anuncio> anuncios;

    public Categoria() {
        this.anuncios = new HashSet<Anuncio>();
    }

    public void setCategoriaId(BigInteger categoriaId) {
        this.categoriaId = categoriaId;
    }

    public BigInteger getCategoriaId() {
        return categoriaId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public static Categoria createDtoToEntity(CreateCategoriaDto dto){
        Categoria nova = new Categoria();
        nova.categoria = dto.categoria();
        return nova;
    }
    public static Categoria updateDtoToEntity(UpdateCategoriaDto dto){
        Categoria nova = new Categoria();
        nova.categoria = dto.categoria();
        return nova;
    }
}
