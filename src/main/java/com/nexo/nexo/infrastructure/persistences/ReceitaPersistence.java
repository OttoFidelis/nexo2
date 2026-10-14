package com.nexo.nexo.infrastructure.persistences;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "receita")
@Data
@NoArgsConstructor
public class ReceitaPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaPersistence categoria;
    public ReceitaPersistence(Long id, String descricao, BigDecimal valor, LocalDate data,
            CategoriaPersistence categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    
}
