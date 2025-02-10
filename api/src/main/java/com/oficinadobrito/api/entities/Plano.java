package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.plano.CreatePlanoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_planos")
public class Plano implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger planoId;

    @NotBlank(message = "nome for plano is required")
    private String nome;

    @DecimalMin(value = "0.0", message = "valor balance must be at least 0.0")
    private double valor;

    @Min(value = 0, message = "quantAnuncio must be at least 0")
    @Max(value = 50, message = "quantAnuncio must be at most 120")
    private int quantAnuncio;

    private int duracao;
    
    private boolean adquirido;

    private LocalDate dataValidade;

    private LocalDate dataAdquirido;

    @JsonBackReference
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
    
    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public static Plano createDtoToEntity(CreatePlanoDto dto){
        Plano novo = new Plano();
        novo.setNome(dto.nome());
        novo.setValor(dto.valor());
        novo.setQuantAnuncio(dto.quantAnuncio());
        novo.setAdquirido(dto.adquirido());
        if(novo.adquirido){
            novo.setDataAdquirido(LocalDate.now());
        }
        novo.setDuracao(dto.duracao());
        return novo;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAdquirido() {
        return adquirido;
    }

    public void setAdquirido(boolean adquirido) {
        this.adquirido = adquirido;
    }

    public LocalDate getDataAdquirido() {
        return dataAdquirido;
    }

    public void setDataAdquirido(LocalDate dataAdquirido) {
        this.dataAdquirido = dataAdquirido;
    }
}
