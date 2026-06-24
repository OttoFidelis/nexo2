package com.nexo.nexo.domain.entities.transacao;

import java.math.BigDecimal;

public record Valor(BigDecimal value) {
    public Valor {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor não pode ser nulo ou negativo");
        }
    }
}
