package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.feedback.CreateFeedbackDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "tb_feedbacks")
public class Feedback implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger feedbackId;
    
    @NotEmpty(message = "property 'comentario' cannot be null, is required a value")
    private String remetenteUsername;

    @NotEmpty(message = "property 'comentario' cannot be null, is required a value")
    private String comentario;
    
    @NotEmpty(message = "property 'quantity of stars' cannot be null, is required a value")
    @Min(value = 0, message = "Stars cannot be less than 0")
    @Max(value = 5, message = "Stars cannot be greater than 5")
    private double stars;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "anuncioId")
    private Anuncio anuncio;

    public BigInteger getFeedbackId() {
        return feedbackId;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        if (stars < 0 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 0 and 5");
        }
        this.stars = stars;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public static Feedback createDtoToEntity(CreateFeedbackDto dto){
        Feedback novo = new Feedback();
        novo.remetenteUsername = dto.remetenteUsername();
        novo.stars = dto.stars();
        novo.comentario = dto.comentario();
        return novo;
    }
    
    public String getRemetenteUsername() {
        return remetenteUsername;
    }

    public void setRemetenteUsername(String remetenteUsername) {
        this.remetenteUsername = remetenteUsername;
    }
}
