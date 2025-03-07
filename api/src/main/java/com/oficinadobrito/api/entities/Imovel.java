package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oficinadobrito.api.utils.dtos.imovel.CreateImovelDto;
import com.oficinadobrito.api.utils.enums.Status;
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

    @Column(name = "pdf_file_name")
    private String pdfFileName;
    
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
    
    private String matriculaCartorio;
    
    private String nomeProprietario;
    
    private String cpfCnpjProprietario;
    
    private String rgProprietario;
    
    private String restricoes;
    
    private String nomeCartorioRegistrado;
    
    private String assinaturaControle;

    @NotBlank(message = "A Description of imovel cannot be empty")
    private String sobre;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean isAnunciado;

    @JsonIgnore
    @OneToOne(mappedBy = "imovel")
    private Anuncio anuncio;

    @JsonBackReference
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
        novo.setStatus(Status.PENDENTE);
        novo.setTipo(dto.tipo());
        novo.setSobre(dto.sobre());
        novo.setAnunciado(false);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getMatriculaCartorio() {
        return matriculaCartorio;
    }

    public void setMatriculaCartorio(String matriculaCartorio) {
        this.matriculaCartorio = matriculaCartorio;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getCpfCnpjProprietario() {
        return cpfCnpjProprietario;
    }

    public void setCpfCnpjProprietario(String cpfCnpjProprietario) {
        this.cpfCnpjProprietario = cpfCnpjProprietario;
    }

    public String getRgProprietario() {
        return rgProprietario;
    }

    public void setRgProprietario(String rgProprietario) {
        this.rgProprietario = rgProprietario;
    }

    public String getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }

    public String getNomeCartorioRegistrado() {
        return nomeCartorioRegistrado;
    }

    public void setNomeCartorioRegistrado(String nomeCartorioRegistrado) {
        this.nomeCartorioRegistrado = nomeCartorioRegistrado;
    }

    public String getAssinaturaControle() {
        return assinaturaControle;
    }

    public void setAssinaturaControle(String assinaturaControle) {
        this.assinaturaControle = assinaturaControle;
    }
}
