package com.fatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long>{
    
}
