package com.pedroodake.openfinanceapi.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ListagemController<R> {
    ResponseEntity<Page<R>> listar(Pageable paginacao);
}
