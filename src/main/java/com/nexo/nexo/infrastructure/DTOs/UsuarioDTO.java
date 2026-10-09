package com.nexo.nexo.infrastructure.DTOs;

public record UsuarioDTO(Long id, String nome, String email, String senha) {
    public UsuarioDTO(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
