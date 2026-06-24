package com.nexo.nexo.domain.implementations.categoria;

import java.util.List;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.domain.useCases.categoria.FindAllCategoriaUseCase;

public class FindAllCategoria implements FindAllCategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public FindAllCategoria(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public List<Categoria> execute() {
        return categoriaGateway.findAll();
    }
}
