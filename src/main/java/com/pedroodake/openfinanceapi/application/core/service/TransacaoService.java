package com.pedroodake.openfinanceapi.application.core.service;

import com.pedroodake.openfinanceapi.adapter.in.controller.mapper.TransacaoMapper;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.transacao.DadosCadastroTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosDetalhamentoTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosListagemTransacao;
import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;
import com.pedroodake.openfinanceapi.application.port.out.TransacaoRepository;
import com.pedroodake.openfinanceapi.exception.type.transacao.TransacaoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoService {
    private final TransacaoRepository repository;
    private final TransacaoMapper mapper;

    public TransacaoService(
            TransacaoRepository repository,
            TransacaoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public DadosDetalhamentoTransacao cadastrarTransacao(DadosCadastroTransacao dados) {
        Transacao transacao = mapper.toDomain(dados);
        Transacao saved = repository.save(transacao);
        return mapper.toDetailsDTO(saved);
    }

    public Page<DadosListagemTransacao> listarTransacoes(Long usuarioId, Pageable paginacao) {
        return repository
                .findAllByUsuarioIdOrderByDataTransacaoDesc(usuarioId, paginacao)
                .map(mapper::toListDTO);
    }

    public DadosDetalhamentoTransacao detalharTransacao(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() ->
                        new TransacaoNotFoundException("ID da transação informada não existe!"));
        return mapper.toDetailsDTO(transacao);
    }

    @Transactional
    public void excluirTransacao(Long id) {
        Transacao transacao = repository.findById(id)
                .orElseThrow(() ->
                        new TransacaoNotFoundException("ID da transação informada não existe!"));
        repository.delete(transacao);
    }
}
