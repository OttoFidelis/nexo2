package com.nexo.nexo.infrastructure.DTOs;

import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para representar uma receita")
public record ReceitaDTO(

        @Schema(description = "ID da receita", example = "1")
        Long id,

        @Schema(description = "Descrição da receita", example = "Venda de produto")
        String descricao,

        @Schema(description = "Valor da receita", example = "500.00")
        BigDecimal valor,

        @Schema(description = "Data da receita", example = "2023-06-15")
        LocalDate data,

        @Schema(description = "Categoria da receita", example = "{ \"id\": 1, \"nome\": \"Vendas\", \"descricao\": \"Receitas relacionadas a vendas de produtos\" }")
        CategoriaDTO categoria) {
    public ReceitaDTO(Long id, String descricao, BigDecimal valor, LocalDate data, CategoriaDTO categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }
}
