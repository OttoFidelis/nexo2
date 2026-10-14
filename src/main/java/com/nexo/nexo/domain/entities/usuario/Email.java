package com.nexo.nexo.domain.entities.usuario;

public record Email(String value) {
    public Email(String value){
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }
    }
}
