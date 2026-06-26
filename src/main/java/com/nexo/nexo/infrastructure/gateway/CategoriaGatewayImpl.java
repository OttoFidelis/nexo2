package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaGatewayImpl implements CategoriaGateway {

    private final CategoriaRepository categoriaRepository;

    public CategoriaGatewayImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria save(Categoria categoria) {
        CategoriaPersistence persistence = CategoriaMapper.toPersistence(categoria);
        persistence.setId(null);
        CategoriaPersistence saved = categoriaRepository.save(persistence);
        return CategoriaMapper.toEntity(saved);
    }

    @Override
    public Categoria findById(Long id) {
        CategoriaPersistence persistence = categoriaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Categoria não encontrada")
        );
        return CategoriaMapper.toEntity(persistence);
    }

    @Override
    public List<Categoria> findAll() {
        List<CategoriaPersistence> persistences = categoriaRepository.findAll();
        return persistences.stream()
                .map(CategoriaMapper::toEntity)
                .toList();

    }
}
