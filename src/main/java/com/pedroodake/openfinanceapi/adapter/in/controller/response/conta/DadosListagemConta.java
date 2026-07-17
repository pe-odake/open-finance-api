package com.pedroodake.openfinanceapi.adapter.in.controller.response.conta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Banco;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoConta;
import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemConta(
        Long id,
        Long usuarioId,
        Banco nomeBanco,
        TipoConta tipoConta,
        BigDecimal saldo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime ultimaSincronizacao,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime criadaEm) {
}
