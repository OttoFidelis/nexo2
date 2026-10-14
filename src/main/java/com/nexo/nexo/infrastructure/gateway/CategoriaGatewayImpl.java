package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.categoria.Categoria;
import com.nexo.nexo.domain.entities.usuario.Usuario;
import com.nexo.nexo.domain.gateway.CategoriaGateway;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import com.nexo.nexo.infrastructure.mappers.UsuarioMapper;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import com.nexo.nexo.infrastructure.security.AuthenticatedUserResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaGatewayImpl implements CategoriaGateway {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaGatewayImpl(CategoriaRepository categoriaRepository,  UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Categoria save(Categoria categoria) {
        if (categoria.getUsuario() == null || categoria.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }

        Long authenticatedUserId = AuthenticatedUserResolver.resolveAuthenticatedUserId(usuarioRepository);
        if (!authenticatedUserId.equals(categoria.getUsuario().getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para criar esta categoria");
        }

        UsuarioPersistence usuarioPersistence = usuarioRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        Usuario usuario = UsuarioMapper.toEntity(usuarioPersistence);
        categoria.setUsuario(usuario);

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

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Categoria update(Long id, Categoria categoria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
