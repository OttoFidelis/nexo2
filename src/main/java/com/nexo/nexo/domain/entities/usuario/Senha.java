package com.nexo.nexo.domain.entities.usuario;

public record Senha(String value) {
    public Senha(String value){
        if(!value.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Senha inválida. Deve conter ao menos 8 caracteres, ao menos uma letra maiúscula, no mínimo, um número e pelo menos um caractere especial.");
        }
        else this.value=value;
    }
}
