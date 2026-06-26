package com.nexo.nexo.infrastructure.persistences;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "despesa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaPersistence categoria;
}
