package com.nexo.nexo.infrastructure.services;

import com.nexo.nexo.infrastructure.repositories.DespesaRepository;
import com.nexo.nexo.infrastructure.repositories.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SaldoService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public SaldoService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public BigDecimal gerarSaldo(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalReceitas = receitaRepository.findTotalByUsuarioId(usuarioId);
        BigDecimal totalDespesas = despesaRepository.findTotalByUsuarioId(usuarioId);

        if (totalReceitas == null) {
            totalReceitas = BigDecimal.ZERO;
        }
        if (totalDespesas == null) {
            totalDespesas = BigDecimal.ZERO;
        }

        return totalReceitas.subtract(totalDespesas);
    }
}
