package com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;
import java.time.LocalDateTime;

public record DadosListagemUsuario(
        Long id,
        String nome,
        String login,
        Boolean ativo,
        Perfil perfil,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime criadoEm,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
        LocalDateTime atualizadoEm) {
}