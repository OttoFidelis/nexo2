package com.nexo.nexo.domain.useCases.transacao;

import com.nexo.nexo.domain.entities.transacao.Transacao;

import java.util.List;

public interface FindAllByUsuarioTransacaoUseCase{
    public List<Transacao> execute(Long id);
}
