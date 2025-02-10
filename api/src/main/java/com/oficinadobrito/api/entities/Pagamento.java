package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oficinadobrito.api.utils.dtos.pagamento.CreatePagamentoDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.security.SecureRandom;

@Entity
@Table(name = "tb_pagamentos")
public class Pagamento implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger pagamentoId;

    private final BigInteger numComprovante;

    private String tipo;

    private String cartao;

    private String chavePix;

    private double valor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    
    @JsonManagedReference
    @OneToMany(mappedBy = "pagamento")
    private Set<Plano> planos;

    public Pagamento() {
        this.numComprovante = this.generateRandomBigInteger(32);
        this.planos = new HashSet<>();
    }

    public void setPagamentoId(BigInteger pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public BigInteger getPagamentoId() {
        return pagamentoId;
    }

    public BigInteger getNumComprovante() {
        return numComprovante;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    public static Pagamento createDtoToEntity(CreatePagamentoDto dto){
        Pagamento novo = new Pagamento();
        novo.setTipo(dto.tipo());
        novo.setValor(dto.valor());
        novo.setCartao(dto.cartao());
        novo.setChavePix(dto.chavePix());
        return novo;
    }

    private BigInteger generateRandomBigInteger(int bitLength) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(bitLength, random);
    }
}
