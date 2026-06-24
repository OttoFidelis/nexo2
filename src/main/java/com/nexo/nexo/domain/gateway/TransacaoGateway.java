package com.nexo.nexo.domain.gateway;

import java.util.List;

import com.nexo.nexo.domain.entities.transacao.Transacao;

public interface TransacaoGateway {
    public Transacao findById(Long id);
    public Transacao save(Transacao transacao);
    public List<Transacao> findAll();
}
