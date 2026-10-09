package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.CreateUsuarioUseCase;

public class CreateUsuario implements CreateUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public CreateUsuario(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public Usuario execute(Usuario usuario){
        return usuarioGateway.save(usuario);
    }
}
