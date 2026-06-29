package com.nexo.nexo.domain.useCases.usuario;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.usuario.Usuario;

public interface CreateUsuarioUseCase {
    public Usuario execute(Usuario usuario);
}
