package com.pedroodake.openfinanceapi.adapter.in.controller.request.conta;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Banco;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoConta;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroConta(
        @NotNull
        Long usuarioId,

        @NotNull
        Banco nomeBanco,

        @NotNull
        TipoConta tipoConta) {
}
