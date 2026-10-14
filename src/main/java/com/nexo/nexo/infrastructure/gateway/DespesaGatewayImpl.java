package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import com.nexo.nexo.infrastructure.mappers.DespesaMapper;
import com.nexo.nexo.infrastructure.persistences.CategoriaPersistence;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import com.nexo.nexo.infrastructure.persistences.UsuarioPersistence;
import com.nexo.nexo.infrastructure.repositories.DespesaRepository;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.UsuarioRepository;
import com.nexo.nexo.infrastructure.security.AuthenticatedUserResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaGatewayImpl implements TransacaoGateway {
    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public DespesaGatewayImpl(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private void resolveCategoria(Transacao transacao) {
        if (transacao.getCategoria() == null || transacao.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria da despesa é obrigatória");
        }

        Long authenticatedUserId = AuthenticatedUserResolver.resolveAuthenticatedUserId(usuarioRepository);

        CategoriaPersistence categoriaPersistence = categoriaRepository.findById(transacao.getCategoria().getId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
        if (categoriaPersistence.getUsuario() == null || categoriaPersistence.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário da categoria é obrigatório");
        }

        if (!authenticatedUserId.equals(categoriaPersistence.getUsuario().getId())) {
            throw new IllegalArgumentException("Usuário não autorizado para criar esta despesa");
        }

        UsuarioPersistence usuarioPersistence = usuarioRepository.findById(categoriaPersistence.getUsuario().getId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        categoriaPersistence.setUsuario(usuarioPersistence);
        transacao.setCategoria(CategoriaMapper.toEntity(categoriaPersistence));
    }

    @Override
    public Transacao findById(Long id) {
        DespesaPersistence persistence = despesaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Despesa não encontrada")
        );
        return DespesaMapper.toEntity(persistence);
    }

    @Override
    public Transacao save(Transacao transacao) {
        transacao.setTipo(Tipo.DESPESA);
        resolveCategoria(transacao);
        DespesaPersistence persistence = DespesaMapper.toPersistence(transacao);
        persistence.setId(null);
        DespesaPersistence saved = despesaRepository.save(persistence);
        return DespesaMapper.toEntity(saved);
    }

    @Override
    public List<Transacao> findAll() {
        List<DespesaPersistence> persistences = despesaRepository.findAll();
        return persistences.stream()
                .map(DespesaMapper::toEntity)
                .toList();
    }

    @Override
    public List<Transacao> findAllByUsuarioId(Long id) {
        return despesaRepository.findAllByCategoriaUsuarioId(id).stream()
                .map(DespesaMapper::toEntity)
                .toList();
    }

    @Override
    public void delete(Long id) {
        despesaRepository.deleteById(id);
    }

    @Override
    public Transacao update(Long id, Transacao transacao) {
        DespesaPersistence persistence = despesaRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Despesa não encontrada")
        );
        resolveCategoria(transacao);
        persistence.setDescricao(transacao.getDescricao().value());
        persistence.setValor(transacao.getValor().value());
        persistence.setData(transacao.getData());
        persistence.setCategoria(CategoriaMapper.toPersistence(transacao.getCategoria()));
        DespesaPersistence updated = despesaRepository.save(persistence);
        return DespesaMapper.toEntity(updated);
    }
}
