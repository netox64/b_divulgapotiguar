package com.oficinadobrito.api.entities;

import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.dtos.imovel.UpdateImovelDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;

@Entity
@Table(name = "Imoveis")
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger imovelId;

    @NotBlank(message = "nome for imovel cannot be empty")
    @Size(max = 255, message = "Description cannot be longer than 255 characters")
    @Column(length = 255)
    private String nome;

    @NotBlank(message = "A localização of imovel cannot be empty")
    private String localizacao;

    @NotBlank(message = "A Area of imovel cannot be empty")
    private String area;

    @NotBlank(message = "O tipo of imovel cannot be empty")
    private String tipo;

    @NotBlank(message = "A Description of imovel cannot be empty")
    private String sobre;

    @OneToOne(mappedBy = "imovel")
    private Anuncio anuncio;

    public Imovel() {
    }

    public BigInteger getImovelId() {
        return imovelId;
    }

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public static Imovel createDtoToEntity(CreateImovelDto dto){
        Imovel novo = new Imovel();
        novo.setNome(dto.nome());
        novo.setLocalizacao(dto.localizacao());
        novo.setArea(dto.area());
        novo.setTipo(dto.tipo());
        novo.setSobre(dto.sobre());
        return novo;
    }
    public static Imovel updateDtoToEntity(UpdateImovelDto dto){
        Imovel novo = new Imovel();
        novo.setNome(dto.nome());
        novo.setArea(dto.area());
        novo.setTipo(dto.tipo());
        novo.setSobre(dto.sobre());
        return novo;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }
}
