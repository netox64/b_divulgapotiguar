package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oficinadobrito.api.utils.dtos.categoria.CreateCategoriaDto;
import com.oficinadobrito.api.utils.dtos.categoria.UpdateCategoriaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_categorias")
public class Categoria implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger categoriaId;

    @Column(length = 100)
    @NotEmpty(message = "property 'categoria name' cannot be null, is required a value")
    private String nome;

    @JsonManagedReference
    @ManyToMany(mappedBy = "categorias")
    private Set<Anuncio> anuncios;

    public Categoria() {
        this.anuncios = new HashSet<>();
    }

    public void setCategoriaId(BigInteger categoriaId) {
        this.categoriaId = categoriaId;
    }

    public BigInteger getCategoriaId() {
        return categoriaId;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Categoria createDtoToEntity(CreateCategoriaDto dto){
        Categoria nova = new Categoria();
        nova.nome = dto.nome();
        return nova;
    }
    public static Categoria updateDtoToEntity(UpdateCategoriaDto dto){
        Categoria nova = new Categoria();
        nova.nome = dto.nome();
        return nova;
    }
}
