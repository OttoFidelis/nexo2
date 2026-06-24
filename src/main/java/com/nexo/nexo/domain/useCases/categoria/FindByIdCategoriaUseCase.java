package com.nexo.nexo.domain.useCases.categoria;

import com.nexo.nexo.domain.entities.categoria.Categoria;

public interface FindByIdCategoriaUseCase {
    public Categoria execute(Long id);
}
