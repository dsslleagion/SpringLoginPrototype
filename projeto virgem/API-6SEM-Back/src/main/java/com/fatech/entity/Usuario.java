package com.fatech.entity;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id_usuario;

    @Column(name = "nome_usuario", nullable = false)
    private String nome_usuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "matricula_empresa", nullable = false, unique = true)
    private String matricula_empresa;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipo_usuario;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "delete_at")
    private LocalDateTime delete_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime create_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_at", nullable = false)
    private LocalDateTime update_at;

    public Long getId_usuario() {
        return id_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatricula_empresa() {
        return matricula_empresa;
    }

    public void setMatricula_empresa(String matricula_empresa) {
        this.matricula_empresa = matricula_empresa;
    }

    // public String getSenha() {
    // return senha;
    // }

    // public void setSenha(String senha) {
    // this.senha = senha;
    // }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public LocalDateTime getDelete_at() {
        return delete_at;
    }

    public void setDelete_at(LocalDateTime delete_at) {
        this.delete_at = delete_at;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    // Método para desativar o usuário (soft delete)
    public void desativar() {
        if (this.delete_at == null) {
            this.delete_at = LocalDateTime.now();
        } else {
            this.delete_at = null;
        }
    }

    // Método para verificar se o usuário foi desativado logicamente
    public boolean isStatus() {
        return delete_at != null;
    }

    @PrePersist
    protected void onCreate() {
        // Definir a data de criação apenas se ainda não estiver definida
        if (create_at == null) {
            create_at = LocalDateTime.now();
        }
        // Sempre atualiza a data de atualização
        update_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        // Atualiza o update_at apenas se o usuário não estiver sendo desativado
        if (!isStatus()) {
            update_at = LocalDateTime.now();
        }
    }

}
