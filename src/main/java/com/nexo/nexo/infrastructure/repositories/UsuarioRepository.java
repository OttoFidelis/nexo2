package com.nexo.nexo.infrastructure.repositories;

import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioPersistence, Long> {
    Optional<UsuarioPersistence> findByEmail(String email);
}
