package com.nexo.nexo.infrastructure.gateway;

import com.nexo.nexo.domain.entities.transacao.Tipo;
import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.infrastructure.mappers.ReceitaMapper;
import com.nexo.nexo.infrastructure.persistences.ReceitaPersistence;
import com.nexo.nexo.infrastructure.repositories.CategoriaRepository;
import com.nexo.nexo.infrastructure.repositories.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaGatewayImpl implements TransacaoGateway {
    private final ReceitaRepository receitaRepository;

    public ReceitaGatewayImpl(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
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
}
