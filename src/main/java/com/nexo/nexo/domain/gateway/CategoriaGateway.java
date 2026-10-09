package com.nexo.nexo.domain.gateway;

import java.util.List;

import com.nexo.nexo.domain.entities.categoria.Categoria;

public interface CategoriaGateway {
    public Categoria save(Categoria categoria);
    public Categoria findById(Long id);
    public List<Categoria> findAll();
    public void delete(Long id);
    public Categoria update(Long id, Categoria categoria);
}
