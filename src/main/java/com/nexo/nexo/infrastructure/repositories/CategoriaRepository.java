package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaPersistence, Long> {
    
}