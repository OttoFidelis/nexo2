package com.nexo.nexo.domain.entities.categoria;

public record Nome(String value) {
    public Nome {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
    }
    
}
