package com.nexo.nexo.domain.implementations.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.UpdateTransacaoUseCase;

public class UpdateTransacao implements UpdateTransacaoUseCase {
    private final TransacaoGateway transacaoGateway;

    public UpdateTransacao(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    public Transacao execute(Long id, Transacao transacao) {
        return transacaoGateway.update(id, transacao);
    }
    
}
