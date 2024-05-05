package com.fatech.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamento")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private long id_departamento;

    @Column(name = "nome_departamento", nullable = false, unique = true)
    private String nome_departamento;

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel_id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "delete_at")
    private LocalDateTime delete_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime create_at;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_at", nullable = false)
    private LocalDateTime update_at;

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

    public long getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(long id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getNome_departamento() {
        return nome_departamento;
    }

    public void setNome_departamento(String nome_departamento) {
        this.nome_departamento = nome_departamento;
    }

    public Usuario getResponsavel_id() {
        return responsavel_id;
    }

    public void setResponsavel_id(Usuario responsavel_id) {
        this.responsavel_id = responsavel_id;
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
