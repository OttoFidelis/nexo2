package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.UpdateSenhaUsuarioUseCase;

public class UpdateSenhaUsuario implements UpdateSenhaUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public UpdateSenhaUsuario(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Usuario execute(Long id, String senha) {
        return usuarioGateway.updateSenha(id, senha);
    }
    
}
