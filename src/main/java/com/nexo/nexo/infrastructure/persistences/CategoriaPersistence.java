package com.nexo.nexo.infrastructure.persistences;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categoria")
@Data 
@NoArgsConstructor
public class CategoriaPersistence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioPersistence usuario;

    public CategoriaPersistence(Long id, String nome, String descricao, UsuarioPersistence usuario) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    
}
