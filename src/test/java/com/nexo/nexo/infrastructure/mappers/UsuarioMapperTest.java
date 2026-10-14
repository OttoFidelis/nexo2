package com.nexo.nexo.infrastructure.mappers;

import com.nexo.nexo.domain.entities.usuario.Email;
import com.nexo.nexo.domain.entities.usuario.NomeUsuario;
import com.nexo.nexo.domain.entities.usuario.Senha;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.infrastructure.DTOs.UsuarioDTO;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioMapperTest {

    @Test
    void shouldRejectNullUsuarioPersistence() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> UsuarioMapper.toEntity((UsuarioPersistence) null)
        );

        assertEquals("Usuário é obrigatório", exception.getMessage());
    }

    @Test
    void shouldRejectNullUsuarioNameFromPersistence() {
        UsuarioPersistence persistence = new UsuarioPersistence(2L, null, "maria@example.com", "senha456");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> UsuarioMapper.toEntity(persistence)
        );

        assertEquals("Nome de usuário inválido. Deve conter apenas letras e espaços.", exception.getMessage());
    }

    @Test
    void shouldMapUsuarioDtoWithoutRepositoryLookup() {
        UsuarioDTO dto = new UsuarioDTO(1L, "João", "joao@example.com", "senha123");

        Usuario usuario = UsuarioMapper.toEntity(dto);

        assertEquals(1L, usuario.getId());
        assertEquals("João", usuario.getNome().value());
        assertEquals("joao@example.com", usuario.getEmail().value());
        assertEquals("senha123", usuario.getSenha().value());
    }

    @Test
    void shouldMapUsuarioPersistenceDirectly() {
        UsuarioPersistence persistence = new UsuarioPersistence(2L, "Maria", "maria@example.com", "senha456");

        Usuario usuario = UsuarioMapper.toEntity(persistence);

        assertEquals(2L, usuario.getId());
        assertEquals("Maria", usuario.getNome().value());
        assertEquals("maria@example.com", usuario.getEmail().value());
        assertEquals("senha456", usuario.getSenha().value());
    }

    @Test
    void shouldMapUsuarioToPersistence() {
        Usuario usuario = new Usuario(
                3L,
                new NomeUsuario("Ana"),
                new Email("ana@example.com"),
                new Senha("senha789")
        );

        UsuarioPersistence persistence = UsuarioMapper.toPersistence(usuario);

        assertEquals(3L, persistence.getId());
        assertEquals("Ana", persistence.getNome());
        assertEquals("ana@example.com", persistence.getEmail());
        assertEquals("senha789", persistence.getSenha());
    }
}

