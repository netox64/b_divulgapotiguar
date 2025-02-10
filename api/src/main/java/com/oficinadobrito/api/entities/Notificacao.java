package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oficinadobrito.api.utils.dtos.notificacao.CreateNotificacaoDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "tb_notificacoes")
public class Notificacao implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger notificacaoId;

    @NotNull(message = "assunto cannot be null")
    @Size(min = 5, max = 100, message = "assunto must be between 5 and 100 characters")
    @Column(length = 100)
    private String assunto;

    @NotNull(message = "Name cannot be null")
    private String descricao;

    @Past(message = "Date must be in the past")
    private LocalDate dataEnvio;

    @NotNull(message = "Status cannot be null")
    private String status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    public BigInteger getNotificacaoId() {
        return notificacaoId;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        if (assunto.isBlank()) {
            throw new IllegalArgumentException("A assunto valid required");
        }
        this.assunto = assunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static Notificacao createDtoToEntity(CreateNotificacaoDto dto){
        Notificacao novo = new Notificacao();
        novo.setAssunto(dto.assunto());
        novo.setDescricao(dto.descricao());
        novo.setStatus(dto.status());
        return novo;
    }
}
