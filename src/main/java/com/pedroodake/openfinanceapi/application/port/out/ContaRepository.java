package com.pedroodake.openfinanceapi.application.port.out;

import com.pedroodake.openfinanceapi.application.core.domain.model.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaRepository {

    List<Conta> findAllByNomeBancoAsc();

    Conta save(Conta conta);

    Optional<Conta> findById(Long id);

    Conta getReferenceById(Long id);

    boolean existsById(Long id);

    void delete(Conta conta);
}
