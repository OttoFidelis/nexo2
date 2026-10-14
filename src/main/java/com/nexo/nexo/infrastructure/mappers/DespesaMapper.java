package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.entities.transacao.Valor;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.DespesaDTO;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;

public class DespesaMapper {
    private static Categoria toCategoriaReference(com.nexo.nexo.infrastructure.DTOs.CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            throw new IllegalArgumentException("Categoria da despesa é obrigatória");
        }
        if (categoriaDTO.id() == null) {
            throw new IllegalArgumentException("Categoria da despesa é obrigatória");
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

    public static Transacao toEntity(DespesaPersistence persistence) {
        return new Transacao(
                persistence.getId(),
                new Descricao(persistence.getDescricao()),
                new Valor(persistence.getValor()),
                persistence.getData(),
                CategoriaMapper.toEntity(persistence.getCategoria()),
                Tipo.DESPESA
        );
    }

    public static Transacao toEntity(DespesaDTO dto) {
        return new Transacao(
                dto.id(),
                new Descricao(dto.descricao()),
                new Valor(dto.valor()),
                dto.data(),
                toCategoriaReference(dto.categoria()),
                Tipo.DESPESA
        );
    }

    public static DespesaPersistence toPersistence(Transacao transacao) {
            return new DespesaPersistence(
                transacao.getId(),
                transacao.getDescricao().value(),
                transacao.getValor().value(),
                transacao.getData(),
                CategoriaMapper.toPersistence(transacao.getCategoria())
        );
    }

    public static DespesaPersistence toPersistence(DespesaDTO dto){
        return new DespesaPersistence(
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

    public static DespesaDTO toDTO(DespesaPersistence persistence) {
        return new DespesaDTO(
                persistence.getId(),
                persistence.getDescricao(),
                persistence.getValor(),
                persistence.getData(),
                CategoriaMapper.toDTO(persistence.getCategoria())
        );
    }

    public static DespesaDTO toDTO(Transacao transacao) {
        return new DespesaDTO(
                transacao.getId(),
                transacao.getDescricao().value(),
                transacao.getValor().value(),
                transacao.getData(),
                CategoriaMapper.toDTO(transacao.getCategoria())
        );
    }
}
