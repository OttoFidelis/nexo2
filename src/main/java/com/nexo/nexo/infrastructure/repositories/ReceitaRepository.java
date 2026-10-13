package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<ReceitaPersistence, Long> {
    List<ReceitaPersistence> findAllByCategoriaUsuarioId(Long id);
    @Query("""
        SELECT COALESCE(SUM(r.valor), 0)
        FROM ReceitaPersistence r
        WHERE r.categoria.usuario.id = :usuarioId
    """)
    BigDecimal findTotalByUsuarioId(Long usuarioId);
}