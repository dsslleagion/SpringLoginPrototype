package com.fatech.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LogDTO {
    private Long id;
    private String entrada;
    
    

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String data;

    private int lotacao;

    public LogDTO(Long id, String entrada,  LocalDateTime data, int lotacao) {
        this.id = id;
        this.entrada = entrada;
        this.data = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.lotacao = lotacao;
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

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}