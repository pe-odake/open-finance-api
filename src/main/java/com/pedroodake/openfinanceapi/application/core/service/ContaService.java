package com.pedroodake.openfinanceapi.application.core.service;

import com.pedroodake.openfinanceapi.adapter.in.controller.mapper.ContaMapper;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.conta.DadosCadastroConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosDetalhamentoConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosListagemConta;
import com.pedroodake.openfinanceapi.application.core.domain.model.Conta;
import com.pedroodake.openfinanceapi.application.port.out.ContaRepository;
import com.pedroodake.openfinanceapi.exception.type.conta.ContaNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContaService {
    private final ContaRepository repository;
    private final ContaMapper mapper;

    public ContaService(
            ContaRepository repository,
            ContaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public DadosDetalhamentoConta cadastrarConta(DadosCadastroConta dados) {
        Conta conta = mapper.toDomain(dados);
        Conta saved = repository.save(conta);
        return mapper.toDetailsDTO(saved);
    }

    public List<DadosListagemConta> listarContas() {
        return repository
                .findAllByNomeBancoAsc()
                .stream()
                .map(mapper::toListDTO)
                .toList();
    }

    public DadosDetalhamentoConta detalharConta(Long id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() ->
                        new ContaNotFoundException("ID do usuário informado não existe!"));
        return mapper.toDetailsDTO(conta);
    }

    @Transactional
    public void excluirConta(Long id) {
        Conta conta = repository.findById(id)
                .orElseThrow(() ->
                        new ContaNotFoundException("ID do usuário informado não existe!"));
        conta.delete(conta);
    }
}
