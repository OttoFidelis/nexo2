package com.nexo.nexo.domain.gateway;

import java.util.List;

import com.nexo.nexo.domain.entities.transacao.Transacao;

public interface TransacaoGateway {
    public Transacao findById(Long id);
    public Transacao save(Transacao transacao);
    public List<Transacao> findAll();
    public List<Transacao> findAllByUsuarioId(Long id);
    public void delete(Long id);
    public Transacao update(Long id, Transacao transacao);
}
