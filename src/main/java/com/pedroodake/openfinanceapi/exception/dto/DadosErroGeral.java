package com.pedroodake.openfinanceapi.exception.dto;

import java.time.LocalDateTime;

public record DadosErroGeral(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}
