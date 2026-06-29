package com.nexo.nexo.domain.useCases.usuario;

import com.nexo.nexo.domain.entities.usuario.Usuario;

import java.util.List;

public interface FindAllUsuarioUseCase {
    public List<Usuario> findAll();
}
