package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oficinadobrito.api.utils.dtos.categoria.CreateCategoriaDto;
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

    @NotEmpty(message = "property 'categoria descricao' cannot be null, is required a value")
    private String descricao;

    @JsonBackReference
    @ManyToMany(mappedBy = "categorias")
    private Set<Anuncio> anuncios;

    public Categoria() {
        this.anuncios = new HashSet<>();
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoriaId(BigInteger categoriaId) {
        this.categoriaId = categoriaId;
    }

    public BigInteger getCategoriaId() {
        return categoriaId;
    }

    public static Categoria createDtoToEntity(CreateCategoriaDto dto){
        Categoria nova = new Categoria();
        nova.setNome(dto.nome());
        nova.setDescricao(dto.descricao());
        return nova;
    }

}
