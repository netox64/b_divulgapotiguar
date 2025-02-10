package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oficinadobrito.api.utils.dtos.anuncio.CreateAnuncioDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_anuncios")
public class Anuncio implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger anuncioId;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    private String descricao;

    @NotBlank(message = "Imagem cannot be empty")
    private String imagemPropaganda;

    @NotBlank(message = "Payment type cannot be empty")
    @Size(max = 50, message = "Payment type cannot be more than 50 characters")
    @Column(length = 50)
    private String tipoPagamento;

    @DecimalMin(value = "0.00", message = "stars must be greater than 0")
    private double stars;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private double preco;

    @FutureOrPresent(message = "Announcement date must be in the present or future")
    private LocalDate dataAnuncio;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "imovelId")
    private Imovel imovel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "planoId")
    private Plano plano;

    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "anuncio_categoria", joinColumns={ @JoinColumn(name = "anuncioId")},inverseJoinColumns = {@JoinColumn(name = "categoriaId")})
    private Set<Categoria> categorias;

    @JsonManagedReference
    @OneToMany(mappedBy = "anuncio")
    private Set<Feedback> feedbacks;

    public Anuncio() {
        this.feedbacks = new HashSet<>();
        this.categorias = new HashSet<>();
    }

    public void setAnuncioId(BigInteger anuncioId) {
        this.anuncioId = anuncioId;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public BigInteger getAnuncioId() {
        return anuncioId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemPropaganda() {
        return imagemPropaganda;
    }

    public void setImagemPropaganda(String imagemPropaganda) {
        this.imagemPropaganda = imagemPropaganda;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDate getDataAnuncio() {
        return dataAnuncio;
    }

    public void setDataAnuncio(LocalDate dataAnuncio) {
        this.dataAnuncio = dataAnuncio;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Anuncio createDtoToEntity(CreateAnuncioDto dto){
        Anuncio novo = new Anuncio();
        novo.setTitle(dto.title());
        novo.setDescricao(dto.descricao());
        novo.setImagemPropaganda(dto.imagemPropaganda());
        novo.setTipoPagamento(dto.tipoPagamento());
        novo.setPreco(dto.preco());
        novo.setDataAnuncio(LocalDate.now());
        return novo;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }
    public void addCategoria(Categoria categoriaRecep){
        this.categorias.add(categoriaRecep);
    }
}
