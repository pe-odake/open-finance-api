package com.pedroodake.openfinanceapi.adapter.out.repository.mapper;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.ContaEntity;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.TransacaoEntity;
import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;
import org.springframework.stereotype.Component;

@Component
public class TransacaoEntityMapper {
    public Transacao toDomain(TransacaoEntity entity) {
        return new Transacao(
                entity.getId(),
                entity.getConta().getId(),
                entity.getConta().getNomeBanco() != null ? entity.getConta().getNomeBanco().name() : null,
                entity.getConta().getTipoConta() != null ? entity.getConta().getTipoConta().name() : null,
                entity.getDescricao(),
                entity.getValor(),
                entity.getCategoria(),
                entity.getTipo(),
                entity.getDataTransacao()
        );
    }

    public TransacaoEntity toEntity(Transacao transacao) {
        return new TransacaoEntity(
                transacao.getId(),
                new ContaEntity(transacao.getContaId()),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getCategoria(),
                transacao.getTipo(),
                transacao.getDataTransacao()
        );
    }
}
