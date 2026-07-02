package com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,
        String nome,
        String login,
        Perfil perfil) {
}