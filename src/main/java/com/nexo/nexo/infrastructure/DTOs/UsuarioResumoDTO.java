package com.nexo.nexo.infrastructure.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO resumido do usuário para respostas aninhadas")
public record UsuarioResumoDTO(
        @Schema(description = "ID do usuário", example = "12")
        Long id,

        @Schema(description = "Nome do usuário", example = "Maria")
        String nome,

        @Schema(description = "Email do usuário", example = "maria@example.com")
        String email
) {
    public UsuarioResumoDTO(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }
}