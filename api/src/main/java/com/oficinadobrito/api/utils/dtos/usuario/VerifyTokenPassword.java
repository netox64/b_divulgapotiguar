package com.oficinadobrito.api.utils.dtos.usuario;

public class VerifyTokenPassword {
    
    private String email;
    private boolean verificado;

    public VerifyTokenPassword() {}
    public VerifyTokenPassword(String email, boolean verificado) {
        this.email = email;
        this.verificado = verificado;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isVerificado() {
        return verificado;
    }
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
}
