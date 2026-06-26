package com.nexo.nexo.infrastructure.DTOs;

import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para representar uma despesa")
public record DespesaDTO(

        @Schema(description = "ID da despesa", example = "1")
        Long id,

        @Schema(description = "Descrição da despesa", example = "Compra de supermercado")
        String descricao,

        @Schema(description = "Valor da despesa", example = "150.00")
        BigDecimal valor,

        @Schema(description = "Data da despesa", example = "2023-06-15")
        LocalDate data,

        @Schema(description = "Categoria da despesa", example = "{ \"id\": 1, \"nome\": \"Alimentação\", \"descricao\": \"Despesas relacionadas à alimentação\" }")
        CategoriaDTO categoria
        ) {
    public DespesaDTO(Long id, String descricao, BigDecimal valor, LocalDate data, CategoriaDTO categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }
}
