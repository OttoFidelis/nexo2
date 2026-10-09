package com.nexo.nexo.domain.implementations.categoria;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.domain.useCases.categoria.UpdateCategoriaUseCase;

public class UpdateCategoria implements UpdateCategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public UpdateCategoria(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Long id, Categoria categoria) {
        return categoriaGateway.update(id, categoria);
    }
    
}
