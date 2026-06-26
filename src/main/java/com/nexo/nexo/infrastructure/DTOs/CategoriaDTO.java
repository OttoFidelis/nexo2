package com.nexo.nexo.infrastructure.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar uma categoria")
public record CategoriaDTO(
        @Schema(description = "ID da categoria", example = "1")
        Long id,

        @Schema(description = "Nome da categoria", example = "Alimentação")
        String nome,

        @Schema(description = "Descrição da categoria", example = "Despesas relacionadas à alimentação")
        String descricao
) {
    public CategoriaDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
}
