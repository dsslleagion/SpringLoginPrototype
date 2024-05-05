package com.fatech.security;

import java.util.ArrayList;
import java.util.List;

public class Login {

    private String email;

    private String senha;

    private List<String> autorizacoes;

    private String token;

    public Login() {
        setAutorizacoes(new ArrayList<String>());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAutorizacoes() {
        return autorizacoes;
    }

    public void setAutorizacoes(List<String> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }
    
    
}
