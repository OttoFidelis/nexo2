package com.nexo.nexo.domain.entities.usuario;

public record Email(String value) {
    public Email(String value){
        if (value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.value = value;
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }
    }
}
