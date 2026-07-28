package com.pedroodake.openfinanceapi.adapter.in.controller.request.transacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Categoria;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosCadastroTransacao(
        @NotNull
        Long contaId,

        @NotBlank
        String descricao,

        @NotNull
        @Positive
        BigDecimal valor,

        @NotNull
        Categoria categoria,

        @NotNull
        TipoTransacao tipo,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        @NotNull
        @PastOrPresent
        LocalDateTime dataTransacao) {
}
