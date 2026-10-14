package com.nexo.nexo.domain.entities.usuario;

public record Senha(String value) {
    public Senha(String value){
        if(value==null || value.isBlank() || value.length()<8){
            throw new IllegalArgumentException("Senha inválida");
        }
        else this.value=value;
    }
}
