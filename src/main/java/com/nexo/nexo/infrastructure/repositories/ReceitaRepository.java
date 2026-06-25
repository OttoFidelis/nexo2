package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<ReceitaPersistence, Long> {
    
}