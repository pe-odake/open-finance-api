package com.pedroodake.openfinanceapi.application.port.out;

import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransacaoRepository {

    Page<Transacao> findAllByUsuarioIdOrderByDataTransacaoDesc(Long usuarioId, Pageable paginacao);

    Transacao save(Transacao transacao);

    Optional<Transacao> findById(Long id);

    Transacao getReferenceById(Long id);

    boolean existsById(Long id);

    void delete(Transacao transacao);
}
