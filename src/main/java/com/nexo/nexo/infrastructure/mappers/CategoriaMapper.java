package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.categoria.Nome;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaPersistence persistence){
        return new Categoria(
                persistence.getId(),
                new Nome(persistence.getNome()),
                new Descricao(persistence.getDescricao())
        );
    }

    public static Categoria toEntity(CategoriaDTO dto){
        return new Categoria(
                dto.id(),
                new Nome(dto.nome()),
                new Descricao(dto.descricao())
        );
    }

    public static CategoriaPersistence toPersistence(Categoria categoria){
        return new CategoriaPersistence(
                categoria.getId(),
                categoria.getNome().value(),
                categoria.getDescricao().value()
        );
    }

    public static CategoriaPersistence toPersistence(CategoriaDTO dto){
        return new CategoriaPersistence(
                dto.id(),
                dto.nome(),
                dto.descricao()
        );
    }

    public static CategoriaDTO toDTO(Categoria categoria){
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome().value(),
                categoria.getDescricao().value()
        );
    }

    public static CategoriaDTO toDTO(CategoriaPersistence categoria){
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao()
        );
    }
}
