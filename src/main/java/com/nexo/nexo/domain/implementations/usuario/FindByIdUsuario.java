package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.FindByIdUsuarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class FindByIdUsuario implements FindByIdUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public FindByIdUsuario(UsuarioGateway usuarioGateway){
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Long id) {
        return usuarioGateway.findById(id);
    }
}
