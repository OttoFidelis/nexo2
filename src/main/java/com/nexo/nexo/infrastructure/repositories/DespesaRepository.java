package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<DespesaPersistence, Long> {
    
}
