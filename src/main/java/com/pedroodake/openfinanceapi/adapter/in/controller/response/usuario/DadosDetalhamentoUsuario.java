package com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String login,
        boolean ativo,
        Perfil perfil) {
}