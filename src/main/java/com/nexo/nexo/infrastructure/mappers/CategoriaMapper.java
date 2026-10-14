package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.categoria.Nome;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.entities.valueObjects.Descricao;
import com.nexo.nexo.infrastructure.DTOs.CategoriaDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioResumoDTO;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;

public class CategoriaMapper {
    public static Categoria toEntity(CategoriaPersistence persistence){
        if (persistence == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
        if (persistence.getUsuario() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }
        return new Categoria(
                persistence.getId(),
                new Nome(persistence.getNome()),
                new Descricao(persistence.getDescricao()),
                UsuarioMapper.toEntity(persistence.getUsuario())
        );
    }

    public static Categoria toEntity(CategoriaDTO dto){
        if (dto == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }
        if (dto.usuario() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }
        return new Categoria(
                dto.id(),
                new Nome(dto.nome()),
                new Descricao(dto.descricao()),
            new Usuario(dto.usuario().id())
        );
    }

    public static CategoriaPersistence toPersistence(Categoria categoria){
        return new CategoriaPersistence(
                categoria.getId(),
                categoria.getNome().value(),
                categoria.getDescricao().value(),
                UsuarioMapper.toPersistence(categoria.getUsuario())
        );
    }

    public static CategoriaPersistence toPersistence(CategoriaDTO dto){
        return new CategoriaPersistence(
                dto.id(),
                dto.nome(),
                dto.descricao(),
            dto.usuario() == null ? null : new UsuarioPersistence(dto.usuario().id(), dto.usuario().nome(), dto.usuario().email(), null)
        );
    }

    public static CategoriaDTO toDTO(Categoria categoria){
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome().value(),
                categoria.getDescricao().value(),
            new UsuarioResumoDTO(
                categoria.getUsuario().getId(),
                categoria.getUsuario().getNome() == null ? null : categoria.getUsuario().getNome().value(),
                categoria.getUsuario().getEmail() == null ? null : categoria.getUsuario().getEmail().value()
            )
        );
    }

    public static CategoriaDTO toDTO(CategoriaPersistence categoria){
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
            categoria.getUsuario() == null ? null : new UsuarioResumoDTO(
                categoria.getUsuario().getId(),
                categoria.getUsuario().getNome(),
                categoria.getUsuario().getEmail()
            )
        );
    }
}
