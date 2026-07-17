package com.pedroodake.openfinanceapi.adapter.in.controller.mapper;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.conta.DadosCadastroConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosDetalhamentoConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosListagemConta;
import com.pedroodake.openfinanceapi.application.core.domain.model.Conta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ContaMapper {
    public Conta toDomain(DadosCadastroConta dados) {
        return new Conta(
                null,
                dados.usuarioId(),
                dados.nomeBanco(),
                dados.tipoConta(),
                BigDecimal.ZERO,
                null,
                null
        );
    }

    public DadosListagemConta toListDTO(Conta conta) {
        return new DadosListagemConta(
                conta.getId(),
                conta.getUsuarioId(),
                conta.getNomeBanco(),
                conta.getTipoConta(),
                conta.getSaldo(),
                conta.getUltimaSincronizacao(),
                conta.getCriadaEm()
        );
    }

    public DadosDetalhamentoConta toDetailsDTO(Conta conta) {
        return new DadosDetalhamentoConta(
                conta.getId(),
                conta.getUsuarioId(),
                conta.getNomeBanco(),
                conta.getTipoConta(),
                conta.getSaldo(),
                conta.getUltimaSincronizacao(),
                conta.getCriadaEm()
        );
    }
}
