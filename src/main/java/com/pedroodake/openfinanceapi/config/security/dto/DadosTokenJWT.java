package com.pedroodake.openfinanceapi.config.security.dto;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;

public record DadosTokenJWT(
        String tokenJWT,
        Long id,
        String nome,
        String login,
        Perfil perfil) {
}