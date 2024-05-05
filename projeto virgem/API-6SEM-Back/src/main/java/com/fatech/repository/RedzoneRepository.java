package com.fatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fatech.entity.Redzone;

public interface RedzoneRepository extends JpaRepository<Redzone, Long> {
    
}
