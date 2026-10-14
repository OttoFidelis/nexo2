package com.nexo.nexo.infrastructure.DTOs;

import com.nexo.nexo.infrastructure.repositories.DespesaRepository;
import com.nexo.nexo.infrastructure.repositories.ReceitaRepository;

import java.math.BigDecimal;

public class GerarSaldo {

    private static ReceitaRepository receitaRepository;
    private static DespesaRepository despesaRepository;
    

    public static BigDecimal gerarSaldo(Long id) {
        BigDecimal saldoTotal = BigDecimal.ZERO;
        saldoTotal = saldoTotal.add(receitaRepository.findTotalByUsuarioId(id));
        saldoTotal = saldoTotal.subtract(despesaRepository.findTotalByUsuarioId(id));
        return saldoTotal;
    }
}
