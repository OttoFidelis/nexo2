package com.nexo.nexo.infrastructure.DTOs;

import java.math.BigDecimal;

public record UsuarioDTOResponse(Long id, String nome, String email, BigDecimal saldo, String token) {
    public UsuarioDTOResponse(Long id, String nome, String email, BigDecimal saldo, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.saldo = saldo;
        this.token = token;
    }
}
