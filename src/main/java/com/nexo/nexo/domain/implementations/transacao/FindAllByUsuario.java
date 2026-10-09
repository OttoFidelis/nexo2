package com.nexo.nexo.domain.implementations.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;
import com.nexo.nexo.domain.gateway.TransacaoGateway;
import com.nexo.nexo.domain.useCases.transacao.FindAllByUsuarioTransacaoUseCase;

import java.util.List;

public class FindAllByUsuario implements FindAllByUsuarioTransacaoUseCase {

    private final TransacaoGateway transacaoGateway;

    public FindAllByUsuario(TransacaoGateway transacaoGateway) {
        this.transacaoGateway = transacaoGateway;
    }

    @Override
    public List<Transacao> execute(Long id) {
        return transacaoGateway.findAllByUsuarioId(id);
    }
}
