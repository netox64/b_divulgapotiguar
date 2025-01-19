package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "tb_imoveis")
public class Imovel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger imovelId;

    @NotBlank(message = "nome for imovel cannot be empty")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    @Column(length = 255)
    private String nome;

    @NotBlank(message = "A localização of imovel cannot be empty")
    private String localizacao;

    private Double areaCalculada;

    @NotNull(message = "comprimento is required.")
    @DecimalMin(value = "1.0", inclusive = true, message = "The length must be greater than 1 meter at least.")
    private Double comprimento;

    @NotNull(message = "largura is required.")
    @DecimalMin(value = "1.0", inclusive = true, message = "The width must be greater than 1 meter at least.")
    private Double largura;

    @NotBlank(message = "A imagem of imovel cannot be empty")
    private String imagemImovel;

    @NotBlank(message = "O tipo of imovel cannot be empty")
    private String tipo;

    @NotBlank(message = "A Description of imovel cannot be empty")
    private String sobre;

    private boolean isAnunciado;

    @JsonIgnore
    @OneToOne(mappedBy = "imovel")
    private Anuncio anuncio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.isBlank()) {
            throw new IllegalArgumentException("A name valid required");
        }
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public static Imovel createDtoToEntity(CreateImovelDto dto) {
        Imovel novo = new Imovel();
        novo.setNome(dto.nome());
        novo.setLocalizacao(dto.localizacao());
        novo.setComprimento(dto.comprimento());
        novo.setLargura(dto.largura());
        novo.setAreaCalculada(dto.areaCalculada());
        novo.setImagemImovel(dto.imagem());
        novo.setTipo(dto.tipo());
        novo.setSobre(dto.sobre());
        novo.setAnunciado(false);
        return novo;
    }

    public static Imovel updateDtoToEntity(UpdateImovelDto dto) {
        Imovel novo = new Imovel();
        novo.setNome(dto.nome());
        novo.setComprimento(dto.comprimento());
        novo.setLargura(dto.largura());
        novo.setAreaCalculada(dto.comprimento()* dto.largura());
        novo.setImagemImovel(dto.imagem());
        novo.setTipo(dto.tipo());
        novo.setSobre(dto.sobre());
        novo.setAnunciado(dto.isAnunciado());
        return novo;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Double getComprimento() {
        return comprimento;
    }

    public void setComprimento(Double comprimento) {
        this.comprimento = comprimento;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public Double getAreaCalculada() {
        return areaCalculada;
    }

    public void setAreaCalculada(Double areaCalculada) {
        this.areaCalculada = areaCalculada;
    }

    public String getImagemImovel() {
        return imagemImovel;
    }

    public void setImagemImovel(String imagemImovel) {
        this.imagemImovel = imagemImovel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigInteger getImovelId() {
        return imovelId;
    }

    public boolean isAnunciado() {
        return isAnunciado;
    }

    public void setAnunciado(boolean anunciado) {
        isAnunciado = anunciado;
    }
}
