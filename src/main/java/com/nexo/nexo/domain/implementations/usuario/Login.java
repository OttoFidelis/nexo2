package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.LoginUsuarioUseCase;

public class Login implements LoginUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public Login(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }


    @Override
    public Usuario login(String email, String senha) {
        return usuarioGateway.login(email, senha);
    }
}
