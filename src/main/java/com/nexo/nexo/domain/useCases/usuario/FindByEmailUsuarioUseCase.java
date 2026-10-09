package com.nexo.nexo.domain.useCases.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;


public interface FindByEmailUsuarioUseCase {
    public Usuario execute(String email);
}
