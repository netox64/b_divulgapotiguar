package com.oficinadobrito.api.utils.dtos.usuario;

import com.oficinadobrito.api.utils.validators.cpf.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CreateUserDto {

    @NotEmpty(message = "property 'username' cannot be null, is required a value")
    private String username;

    @Email(message = "property 'email' cannot be null, is required a value, a valid email")
    private String email;

    @NotEmpty(message = "property 'phone' cannot be null, is required a value")
    private String phone;

    @NotEmpty(message = "property 'cpf' cannot be null, is required a value")
    @Size(min = 11, max = 14, message = "cpf must be between 2 and 50 characters")
    @CPF(message = "property 'cpf' must be a valid CPF")
    private String cpf;

    @NotEmpty(message = "property 'Password' cannot be null, is required a value")
    private String password;

    public CreateUserDto(String user, String mail, String s) {
        this.username = user;
        this.email = mail;
        this.password = s;
    }

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
}
