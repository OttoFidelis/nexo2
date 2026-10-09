package com.nexo.nexo.infrastructure.repositories;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DespesaRepository extends JpaRepository<DespesaPersistence, Long> {
    List<DespesaPersistence> findAllByCategoriaUsuarioId(Long id);
    @Query("""
        SELECT COALESCE(SUM(d.valor), 0)
        FROM DespesaPersistence d
        WHERE d.categoria.usuario.id = :usuarioId
    """)
    BigDecimal sumByUsuarioId(Long usuarioId);
}
