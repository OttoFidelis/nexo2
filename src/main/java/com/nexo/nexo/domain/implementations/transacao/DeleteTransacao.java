package com.nexo.nexo.domain.implementations.transacao;

import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.DeleteTransacaoUseCase;

public class DeleteTransacao implements DeleteTransacaoUseCase {
    private final TransacaoGateway transacaoGateway;

    public DeleteTransacao(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    public void execute(Long id) {
        transacaoGateway.delete(id);
    }
    
}
