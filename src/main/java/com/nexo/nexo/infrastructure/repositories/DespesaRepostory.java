package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.oersistences.DespesaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepostory extends JpaRepository<DespesaPersistence, Long> {
    
}
