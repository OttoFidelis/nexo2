package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.entities.transacao.Valor;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.DespesaDTO;
import com.nexo.nexo.infrastructure.DTOs.DespesaDTO;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;

public class DespesaMapper {
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
                CategoriaMapper.toEntity(dto.categoria()),
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
                CategoriaMapper.toPersistence(dto.categoria())
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
