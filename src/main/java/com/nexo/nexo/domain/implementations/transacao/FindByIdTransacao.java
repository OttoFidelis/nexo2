package com.nexo.nexo.domain.implementations.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.FindByIdTransacaoUseCase;

public class FindByIdTransacao implements FindByIdTransacaoUseCase {
    private final TransacaoGateway transacaoGateway;

    public FindByIdTransacao(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    public Transacao execute(Long id) {
        return transacaoGateway.findById(id);
    }
}
