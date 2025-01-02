package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.plano.CreatePlanoDto;
import com.oficinadobrito.api.utils.dtos.plano.UpdatePlanoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger planoId;

    @DecimalMin(value = "0.0", message = "valor balance must be at least 0.0")
    private double valor;

    @Min(value = 0, message = "quantAnuncio must be at least 0")
    @Max(value = 50, message = "quantAnuncio must be at most 120")
    private int quantAnuncio;

    private LocalDate dataAdquerido;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pagamentoId")
    private Pagamento pagamento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private Set<Anuncio> anuncios;

    public Plano() {
        this.anuncios = new HashSet<>();
    }

    public void setPlanoId(BigInteger planoId) {
        this.planoId = planoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigInteger getPlanoId() {
        return planoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if(valor < 0 ){
            throw  new IllegalArgumentException("quantity of anuncio required > 0");
        }
        this.valor = valor;
    }

    public int getQuantAnuncio() {
        return quantAnuncio;
    }

    public void setQuantAnuncio(int quantAnuncio) {
        if(quantAnuncio <0 || quantAnuncio > 50 ){
            throw  new IllegalArgumentException("quantity of anuncio required > 0");
        }
        this.quantAnuncio = quantAnuncio;
    }

    public LocalDate getDataAdquerido() {
        return dataAdquerido;
    }

    public void setDataAdquerido(LocalDate dataAdquerido) {
        this.dataAdquerido = dataAdquerido;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public static Plano createDtoToEntity(CreatePlanoDto dto){
        Plano novo = new Plano();
        novo.setValor(dto.valor());
        novo.setQuantAnuncio(dto.quantAnuncio());
        novo.setDataAdquerido(dto.dataAdquerido());
        return novo;
    }
    public static Plano updateDtoToEntity(UpdatePlanoDto dto){
        Plano novo = new Plano();
        novo.setValor(dto.valor());
        novo.setQuantAnuncio(dto.quantAnuncio());
        return novo;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }
}
