package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.usuario.Email;
import com.nexo.nexo.domain.entities.usuario.NomeUsuario;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTO;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTOResponse;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;

import java.math.BigDecimal;

public class UsuarioMapper {
    public static Usuario toEntity(UsuarioPersistence persistence) {
        if (persistence == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        return new Usuario(
                persistence.getId(),
                new NomeUsuario(persistence.getNome()),
                new Email(persistence.getEmail()),
                new Senha(persistence.getSenha())
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        return new Usuario(
                dto.id(),
                new NomeUsuario(dto.nome()),
                new Email(dto.email()),
                new Senha(dto.senha())
        );
    }

    public static UsuarioPersistence toPersistence(Usuario usuario) {
        return new UsuarioPersistence(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value()
        );
    }

    public static UsuarioPersistence toPersistence(UsuarioDTO usuarioDTO) {
        return new UsuarioPersistence(
                usuarioDTO.id(),
                usuarioDTO.nome(),
                usuarioDTO.email(),
                usuarioDTO.senha()
        );
    }

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                usuario.getSenha().value()
        );
    }

    public static UsuarioDTO toDTO(UsuarioPersistence persistence){
        return new UsuarioDTO(
                persistence.getId(),
                persistence.getNome(),
                persistence.getEmail(),
                persistence.getSenha()
        );
    }

    public static UsuarioDTOResponse toDTOResponse(Usuario usuario, String token) {
        return toDTOResponse(usuario, token, BigDecimal.ZERO);
    }

    public static UsuarioDTOResponse toDTOResponse(Usuario usuario, String token, BigDecimal saldo) {
        return new UsuarioDTOResponse(
                usuario.getId(),
                usuario.getNome().value(),
                usuario.getEmail().value(),
                saldo == null ? BigDecimal.ZERO : saldo,
                token
        );
    }
}
