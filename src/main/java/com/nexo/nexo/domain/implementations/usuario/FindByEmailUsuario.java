package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.FindByEmailUsuarioUseCase;


public class FindByEmailUsuario implements FindByEmailUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public FindByEmailUsuario(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(String email) {
        return usuarioGateway.findByEmail(email);
    }
}
