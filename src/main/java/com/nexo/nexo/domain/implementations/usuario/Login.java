package com.nexo.nexo.domain.implementations.usuario;

import com.nexo.nexo.domain.gateway.UsuarioGateway;
import com.nexo.nexo.domain.useCases.usuario.LoginUsuarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class Login implements LoginUsuarioUseCase {
    private final UsuarioGateway usuarioGateway;

    public Login(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }


    @Override
    public String login(String email, String senha) {
        return usuarioGateway.login(email, senha);
    }
}
