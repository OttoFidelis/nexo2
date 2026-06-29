package com.nexo.nexo.domain.entities.usuario;

public record NomeUsuario (String value){
    public NomeUsuario (String value){
        if(value.matches("^[a-zA-ZÀ-ÿ\\s]+$")){
            this.value = value;
        } else {
            throw new IllegalArgumentException("Nome de usuário inválido. Deve conter apenas letras e espaços.");
        }
    }
}
