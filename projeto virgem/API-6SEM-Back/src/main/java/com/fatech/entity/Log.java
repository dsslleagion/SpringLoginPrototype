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
import jakarta.persistence.Table;



@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entrada", nullable = false)
    private Boolean entrada;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "lotacao", nullable = false)
    private int lotacao;

    @ManyToOne
    @JoinColumn(name = "id_redzone", nullable = false)
    private Redzone redzoneId;

    public Redzone getRedzoneId() {
        return redzoneId;
    }

    public void setRedzoneId(Redzone redzoneId) {
        this.redzoneId = redzoneId;
    }

    public Long getId() {
        return id;
    }

    public int getLotacao() {
        return lotacao;
    }

    public void setLotacao(int lotacao) {
        this.lotacao = lotacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getEntradaAsString() {
        if (this.entrada != null) {
            return this.entrada ? "entrada" : "saida";
        } else {
            return null;
        }
    }

    
}