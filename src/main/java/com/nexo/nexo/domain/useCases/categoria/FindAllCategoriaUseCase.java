package com.nexo.nexo.domain.useCases.categoria;

import java.util.List;

import com.nexo.nexo.domain.entities.categoria.Categoria;

public interface FindAllCategoriaUseCase {
    public List<Categoria> execute();
}
