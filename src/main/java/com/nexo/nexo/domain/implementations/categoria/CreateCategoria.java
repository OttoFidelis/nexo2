package com.nexo.nexo.domain.implementations.categoria;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.domain.useCases.categoria.CreateCategoriaUseCase;

public class CreateCategoria implements CreateCategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public CreateCategoria(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public Categoria execute(Categoria categoria) {
        return categoriaGateway.save(categoria);
    }
}
