package com.pedroodake.openfinanceapi.application.port.in;

import org.springframework.http.ResponseEntity;

public interface ExclusaoController<D, ID> {
    ResponseEntity<D> excluir(ID id);
}
