package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.mappers.CategoriaMapper;
import com.nexo.nexo.infrastructure.mappers.DespesaMapper;
import com.nexo.nexo.infrastructure.persistences.DespesaPersistence;
import com.nexo.nexo.infrastructure.repositories.DespesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaGatewayImpl implements TransacaoGateway {
    private final DespesaRepository despesaRepository;

    public DespesaGatewayImpl(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
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
        persistence.setDescricao(transacao.getDescricao().value());
        persistence.setValor(transacao.getValor().value());
        persistence.setData(transacao.getData());
        persistence.setCategoria(CategoriaMapper.toPersistence(transacao.getCategoria()));
        DespesaPersistence updated = despesaRepository.save(persistence);
        return DespesaMapper.toEntity(updated);
    }
}
