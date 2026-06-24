package com.nexo.nexo.domain.implementations.transacao;

import java.util.List;

import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.FindAllTransacaoUseCase;

public class FindAllTransacao implements FindAllTransacaoUseCase {
    private final TransacaoGateway transacaoGateway;

    public FindAllTransacao(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    public List<Transacao> execute() {
        return transacaoGateway.findAll();
    }
}
