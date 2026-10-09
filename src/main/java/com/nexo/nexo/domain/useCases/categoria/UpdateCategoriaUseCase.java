package com.nexo.nexo.domain.useCases.categoria;

import com.nexo.nexo.domain.entities.categoria.Categoria;

public interface UpdateCategoriaUseCase {
    public Categoria execute(Long id, Categoria categoria);
}
