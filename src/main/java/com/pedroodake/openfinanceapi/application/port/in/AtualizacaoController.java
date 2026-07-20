package com.pedroodake.openfinanceapi.application.port.in;

import org.springframework.http.ResponseEntity;

public interface AtualizacaoController<U, DET> {
    ResponseEntity<DET> atualizar(U dados);
}
