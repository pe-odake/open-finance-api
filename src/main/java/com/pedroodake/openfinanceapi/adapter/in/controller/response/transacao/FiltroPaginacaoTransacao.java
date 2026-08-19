package com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.YearMonth;

public record FiltroPaginacaoTransacao(
        String descricao,
        String nomeBanco,
        String categoria

//        @DateTimeFormat(pattern = "yyyy-MM")
//        YearMonth mes
) {}