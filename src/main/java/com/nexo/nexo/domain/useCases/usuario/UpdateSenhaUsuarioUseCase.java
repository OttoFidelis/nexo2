package com.nexo.nexo.domain.useCases.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;

public interface UpdateSenhaUsuarioUseCase {
    Usuario execute(Long id, String senha); 
}
