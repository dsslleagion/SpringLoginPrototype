package com.fatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Departamento;

public interface DepartamentoRepository extends JpaRepository <Departamento, Long> {
    
}
