package com.nexo.nexo.domain.useCases.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;

public interface CreateTransacaoUseCase {
    public Transacao execute(Transacao transacao);
}
