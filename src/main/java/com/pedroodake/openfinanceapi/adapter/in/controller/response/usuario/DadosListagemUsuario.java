package com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;

public record DadosListagemUsuario(
        Long id,
        String nome,
        String login,
        Boolean ativo,
        Perfil perfil) {
}