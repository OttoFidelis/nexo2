package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.entities.transacao.Valor;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.DTOs.ReceitaDTO;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;

public class ReceitaMapper {
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
        return new Transacao(
                dto.id(),
                new Descricao(dto.descricao()),
                new Valor(dto.valor()),
                dto.data(),
                CategoriaMapper.toEntity(dto.categoria()),
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
                CategoriaMapper.toPersistence(dto.categoria())
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
