package com.fatech.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fatech.entity.Log;
import com.fatech.entity.Redzone;

public interface LogRepository extends JpaRepository<Log, Long>{
    List<Log> findByRedzoneId(Redzone redzoneId);
    
    @Query("SELECT l FROM Log l WHERE l.redzoneId = :redzoneId AND l.data BETWEEN :startDate AND :endDate")
    List<Log> findByRedzoneIdAndDateRange(@Param("redzoneId") Redzone redzoneId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}