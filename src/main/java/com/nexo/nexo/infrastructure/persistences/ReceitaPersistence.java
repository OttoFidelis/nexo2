package com.nexo.nexo.infrastructure.persistences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "receita")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceitaPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaPersistence categoria;
}
