package com.pedroodake.openfinanceapi.application.port.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

public interface CadastroController<C, DET> {
    ResponseEntity<DET> cadastrar(C dados, UriComponentsBuilder uriBuilder);
}
