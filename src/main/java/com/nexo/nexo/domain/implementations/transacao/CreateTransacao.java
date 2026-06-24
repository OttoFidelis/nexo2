package com.nexo.nexo.domain.implementations.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.CreateTransacaoUseCase;

public class CreateTransacao implements CreateTransacaoUseCase {
    private final TransacaoGateway transacaoGateway;

    public CreateTransacao(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    public Transacao execute(Transacao transacao) {
        return transacaoGateway.save(transacao);
    }
}
