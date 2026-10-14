package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import com.nexo.nexo.infrastructure.mappers.ReceitaMapper;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.ReceitaRepository;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import com.nexo.nexo.infrastructure.security.AuthenticatedUserResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaGatewayImpl implements TransacaoGateway {
    private final ReceitaRepository receitaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ReceitaGatewayImpl(ReceitaRepository receitaRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.receitaRepository = receitaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private void resolveCategoria(Transacao transacao) {
        if (transacao.getCategoria() == null || transacao.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria da receita é obrigatória");
        }

        Long authenticatedUserId = AuthenticatedUserResolver.resolveAuthenticatedUserId(usuarioRepository);

        CategoriaPersistence categoriaPersistence = categoriaRepository.findById(transacao.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
        if (categoriaPersistence.getUsuario() == null || categoriaPersistence.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }

        if (!authenticatedUserId.equals(categoriaPersistence.getUsuario().getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para criar esta receita");
        }

        UsuarioPersistence usuarioPersistence = usuarioRepository.findById(categoriaPersistence.getUsuario().getId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        categoriaPersistence.setUsuario(usuarioPersistence);
        transacao.setCategoria(CategoriaMapper.toEntity(categoriaPersistence));
    }

    @Override
    public Transacao findById(Long id) {
        ReceitaPersistence persistence = receitaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Receita não encontrada")
        );
        return ReceitaMapper.toEntity(persistence);
    }

    @Override
    public Transacao save(Transacao transacao) {
        transacao.setTipo(Tipo.RECEITA);
        resolveCategoria(transacao);
        ReceitaPersistence persistence = ReceitaMapper.toPersistence(transacao);
        persistence.setId(null);
        ReceitaPersistence saved = receitaRepository.save(persistence);
        return ReceitaMapper.toEntity(saved);
    }

    @Override
    public List<Transacao> findAll() {
        List<ReceitaPersistence> persistences = receitaRepository.findAll();
        return persistences.stream()
                .map(ReceitaMapper::toEntity)
                .toList();
    }

    @Override
    public List<Transacao> findAllByUsuarioId(Long id) {
        List<ReceitaPersistence> persistences = receitaRepository.findAllByCategoriaUsuarioId(id);
        return persistences.stream()
                .map(ReceitaMapper::toEntity)
                .toList();
    }

    @Override
    public void delete(Long id) {
        receitaRepository.deleteById(id);
    }

    @Override
    public Transacao update(Long id, Transacao transacao) {
        ReceitaPersistence persistence = receitaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Receita não encontrada")
        );
        resolveCategoria(transacao);
        persistence.setDescricao(transacao.getDescricao().value());
        persistence.setValor(transacao.getValor().value());
        persistence.setData(transacao.getData());
        persistence.setCategoria(CategoriaMapper.toPersistence(transacao.getCategoria()));
        ReceitaPersistence updated = receitaRepository.save(persistence);
        return ReceitaMapper.toEntity(updated);
    }
}
