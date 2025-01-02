package com.oficinadobrito.api.utils.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CreateUserDto {

    @NotEmpty(message = "property 'username' cannot be null, is required a value")
    private String username;

    @Email(message = "property 'email' cannot be null, is required a value, a valid email")
    private String email;

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

}
