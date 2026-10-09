package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.DeleteUsuarioUseCase;

public class DeleteUsuario implements DeleteUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public DeleteUsuario(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void execute(Long id) {
        usuarioGateway.delete(id);
    }
    
}
