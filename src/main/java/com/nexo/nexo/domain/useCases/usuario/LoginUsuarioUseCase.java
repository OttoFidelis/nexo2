package com.nexo.nexo.domain.useCases.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;

public interface LoginUsuarioUseCase {
    public Usuario login(String email, String senha);
}
