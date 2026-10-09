package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.FindAllUsuarioUseCase;

import java.util.List;

public class FindAllUsuario implements FindAllUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public FindAllUsuario(UsuarioGateway usuarioGateway){
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public List<Usuario> execute() {
        return usuarioGateway.findAll();
    }
}
