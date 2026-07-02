package com.pedroodake.openfinanceapi.application.port.in;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosLogin;
import com.pedroodake.openfinanceapi.config.security.dto.DadosTokenJWT;
import org.springframework.http.ResponseEntity;

public interface LoginPort {
    ResponseEntity<DadosTokenJWT> logar(DadosLogin dados);
}