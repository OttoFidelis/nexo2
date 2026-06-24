package com.nexo.nexo.domain.useCases.transacao;

import java.util.List;

import com.nexo.nexo.domain.entities.transacao.Transacao;

public interface FindAllTransacaoUseCase {
    public List<Transacao> execute();
}
