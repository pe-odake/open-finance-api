package com.pedroodake.openfinanceapi.adapter.out.repository.persistence;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.TransacaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransacaoJpaRepository extends JpaRepository<TransacaoEntity, Long>, JpaSpecificationExecutor<TransacaoEntity> {
//    Page<TransacaoEntity> findAllByOrderByDataTransacaoDesc(Pageable paginacao);

    Page<TransacaoEntity> findAllByContaUsuarioIdOrderByDataTransacaoDesc(Long usuarioId, Pageable paginacao);
}
