package com.nexo.nexo.domain.implementations.categoria;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.domain.useCases.categoria.FindByIdCategoriaUseCase;

public class FindByIdCategoria implements FindByIdCategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public FindByIdCategoria(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Long id) {
        return categoriaGateway.findById(id);
    }
}
