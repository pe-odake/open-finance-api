package com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Categoria;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoTransacao;
import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemTransacao(
        Long id,
        Long contaId,
        String nomeBanco,
        String tipoConta,
        String descricao,
        BigDecimal valor,
        Categoria categoria,
        TipoTransacao tipo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime dataTransacao) {
}
