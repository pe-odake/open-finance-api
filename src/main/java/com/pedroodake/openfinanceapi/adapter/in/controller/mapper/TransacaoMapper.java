package com.pedroodake.openfinanceapi.adapter.in.controller.mapper;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.transacao.DadosCadastroTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosDetalhamentoTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosListagemTransacao;
import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {
    public Transacao toDomain(DadosCadastroTransacao dados) {
        return new Transacao(
                null,
                dados.contaId(),
                null,
                null,
                dados.descricao(),
                dados.valor(),
                dados.categoria(),
                dados.tipo(),
                dados.dataTransacao()
        );
    }

    public DadosListagemTransacao toListDTO(Transacao transacao) {
        return new DadosListagemTransacao(
                transacao.getId(),
                transacao.getContaId(),
                transacao.getNomeBanco(),
                transacao.getTipoConta(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getCategoria(),
                transacao.getTipo(),
                transacao.getDataTransacao()
        );
    }

    public DadosDetalhamentoTransacao toDetailsDTO(Transacao transacao) {
        return new DadosDetalhamentoTransacao(
                transacao.getId(),
                transacao.getContaId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getCategoria(),
                transacao.getTipo(),
                transacao.getDataTransacao()
        );
    }
}