package com.nexo.nexo.domain.entities.valueObjects;

public record Descricao (String value) {
    public Descricao {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
    }
}
