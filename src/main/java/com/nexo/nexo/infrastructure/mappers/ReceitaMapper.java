package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.entities.transacao.Valor;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.ReceitaDTO;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;

public class ReceitaMapper {
    private static Categoria toCategoriaReference(com.nexo.nexo.infrastructure.DTOs.CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            throw new IllegalArgumentException("Categoria da receita é obrigatória");
        }
        if (categoriaDTO.id() == null) {
            throw new IllegalArgumentException("Categoria da receita é obrigatória");
        }
        if (categoriaDTO.usuario() == null || categoriaDTO.usuario().id() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }
        return new Categoria(
                categoriaDTO.id(),
                null,
                null,
                new Usuario(categoriaDTO.usuario().id())
        );
    }

    public static Transacao toEntity(ReceitaPersistence persistence) {
        return new Transacao(
                persistence.getId(),
                new Descricao(persistence.getDescricao()),
                new Valor(persistence.getValor()),
                persistence.getData(),
                CategoriaMapper.toEntity(persistence.getCategoria()),
                Tipo.RECEITA
        );
    }

    public static Transacao toEntity(ReceitaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Receita não pode ser nula");
        }

        if (dto.categoria() == null) {
            throw new IllegalArgumentException("Categoria da receita é obrigatória");
        }

        return new Transacao(
                dto.id(),
                new Descricao(dto.descricao()),
                new Valor(dto.valor()),
                dto.data(),
            toCategoriaReference(dto.categoria()),
                Tipo.RECEITA
        );
    }

    public static ReceitaPersistence toPersistence(Transacao transacao) {
            return new ReceitaPersistence(
                transacao.getId(),
                transacao.getDescricao().value(),
                transacao.getValor().value(),
                transacao.getData(),
                CategoriaMapper.toPersistence(transacao.getCategoria())
        );
    }

    public static ReceitaPersistence toPersistence(ReceitaDTO dto){
        return new ReceitaPersistence(
                dto.id(),
                dto.descricao(),
                dto.valor(),
                dto.data(),
            new CategoriaPersistence(
                dto.categoria() == null ? null : dto.categoria().id(),
                null,
                null,
                dto.categoria() == null || dto.categoria().usuario() == null ? null : new UsuarioPersistence(dto.categoria().usuario().id(), null, null, null)
            )
        );
    }

    public static ReceitaDTO toDTO(ReceitaPersistence persistence) {
        return new ReceitaDTO(
                persistence.getId(),
                persistence.getDescricao(),
                persistence.getValor(),
                persistence.getData(),
                CategoriaMapper.toDTO(persistence.getCategoria())
        );
    }

    public static ReceitaDTO toDTO(Transacao transacao) {
        return new ReceitaDTO(
                transacao.getId(),
                transacao.getDescricao().value(),
                transacao.getValor().value(),
                transacao.getData(),
                CategoriaMapper.toDTO(transacao.getCategoria())
        );
    }

}
