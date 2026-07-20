package com.pedroodake.openfinanceapi.application.port.in;

import org.springframework.http.ResponseEntity;

public interface DetalhamentoController<DET, ID> {
    ResponseEntity<DET> detalhar(ID id);
}
