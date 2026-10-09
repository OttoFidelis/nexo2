package com.nexo.nexo.infrastructure.DTOs;

import java.math.BigDecimal;

public record UsuarioDTOResponse(Long id, String nome, String email, String senha, BigDecimal saldo) {
    public UsuarioDTOResponse(Long id, String nome, String email, String senha, BigDecimal saldo)  {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        saldo = GerarSaldo.gerarSaldo(id);
        this.saldo = saldo;
    }
}
