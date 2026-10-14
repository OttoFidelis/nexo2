package com.nexo.nexo.domain.entities.usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class UsuarioTest {

    @Test
    void shouldIgnoreZeroIdWhenCreatingUser() {
        Usuario usuario = new Usuario();
        usuario.setId(0L);

        assertNull(usuario.getId());
    }
}
