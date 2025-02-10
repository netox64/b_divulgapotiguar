package com.oficinadobrito.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oficinadobrito.api.config.listeners.UsuarioListener;
import com.oficinadobrito.api.utils.dtos.usuario.CreateUserDto;
import com.oficinadobrito.api.utils.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EntityListeners(UsuarioListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    private String usuarioId;

    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Column(length = 50)
    private String username;
    
    private String image;

    @NotNull(message = "phone cannot be null")
    @Pattern(regexp = "^\\(\\d{2}\\) \\d{5}-\\d{4}$", message = "Invalid number")
    private String phone;

    @Min(value = 0, message = "Stars cannot be less than 0")
    @Max(value = 5, message = "Stars cannot be greater than 5")
    private double mediaStars;

    @NotNull(message = "cpf cannot be null")
    @Column(length = 255)
    private String cpf;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Column(length = 250, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long, and max 255 characters")
    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String token;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private Set<Notificacao> notificacoes;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private Set<Feedback> feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<Pagamento> pagamentos;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private Set<Plano> planos;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private Set<Anuncio> anuncios;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario")
    private Set<Imovel> imoveis;

    public Usuario() {
        this.notificacoes = new HashSet<>();
        this.feedbacks = new HashSet<>();
        this.pagamentos = new HashSet<>();
        this.planos = new HashSet<>();
        this.anuncios = new HashSet<>();
        this.imoveis = new HashSet<>();
    }

    public Usuario(String username, String email, String phone, String cpf, String password, UserRole role) {
        this();
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ADMIN"),new SimpleGrantedAuthority("USER"));
        else
            return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(Set<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(Set<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Set<Plano> getPlanos() {
        return planos;
    }

    public void setPlanos(Set<Plano> planos) {
        this.planos = planos;
    }

    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static Usuario toEntity(CreateUserDto dto) {
        return new Usuario(dto.getUsername(), dto.getEmail(), dto.getPhone(), dto.getCpf(), dto.getPassword(),
                UserRole.USER);
    }

    public Set<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(Set<Imovel> imoveis) {
        this.imoveis = imoveis;
    }

    public void addNotificacao(Notificacao notificacao) {
        this.notificacoes.add(notificacao);
    }

    public void addPlano(Plano plano) {
        this.planos.add(plano);
    }

    public double getMediaStars() {
        return mediaStars;
    }

    public void setMediaStars(double mediaStars) {
        this.mediaStars = mediaStars;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
