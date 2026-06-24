package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.oersistences.ReceitaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaPersistence, Long> {
    
}