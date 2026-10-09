package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.usuario.Email;
import com.nexo.nexo.domain.entities.usuario.NomeUsuario;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTOResponse;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioPersistence persistence) {
        Usuario usuario = new Usuario(
                persistence.getId(),
                new NomeUsuario(persistence.getNome()),
                new Email(persistence.getEmail()),
                new Senha(persistence.getSenha())
        );
        return usuario;
    }

    public static Usuario toEntity(UsuarioDTOResponse dto) {
        Usuario usuario = new Usuario(
                dto.id(),
                new NomeUsuario(dto.nome()),
                new Email(dto.email()),
                new Senha(dto.senha())
        );
        return usuario;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario(
                dto.id(),
                new NomeUsuario(dto.nome()),
                new Email(dto.email()),
                new Senha(dto.senha())
        );
        return usuario;
    }

    public static UsuarioPersistence toPersistence(Usuario usuario) {
        UsuarioPersistence persistence = new UsuarioPersistence(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value()
        );
        return persistence;
    }

    public static UsuarioPersistence toPersistence(UsuarioDTO usuarioDTO) {
        UsuarioPersistence persistence = new UsuarioPersistence(
                usuarioDTO.id(),
                usuarioDTO.nome(),
                usuarioDTO.email(),
                usuarioDTO.senha()
        );
        return persistence;
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value()
        );
        return usuarioDTO;
    }

    public static UsuarioDTO toDTO(UsuarioPersistence persistence){
        UsuarioDTO usuarioDTO = new UsuarioDTO(
                persistence.getId(),
                persistence.getNome(),
                persistence.getEmail(),
                persistence.getSenha()
        );
        return usuarioDTO;
    }

    public static UsuarioDTOResponse toDTOResponse(Usuario  usuario) {
        UsuarioDTOResponse usuarioDTOResponse = new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value(),
                null
        );
        return usuarioDTOResponse;
    }
}
