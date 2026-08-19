package com.pedroodake.openfinanceapi.adapter.out.repository;

import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.FiltroPaginacaoTransacao;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.TransacaoEntity;
import com.pedroodake.openfinanceapi.adapter.out.repository.mapper.TransacaoEntityMapper;
import com.pedroodake.openfinanceapi.adapter.out.repository.persistence.TransacaoJpaRepository;
import com.pedroodake.openfinanceapi.adapter.out.repository.specification.TransacaoSpecification;
import com.pedroodake.openfinanceapi.application.core.domain.model.Transacao;
import com.pedroodake.openfinanceapi.application.port.out.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransacaoRepositoryImpl implements TransacaoRepository {
    private final TransacaoJpaRepository jpaRepository;
    private final TransacaoEntityMapper entityMapper;

    public TransacaoRepositoryImpl(
            TransacaoJpaRepository jpaRepository,
            TransacaoEntityMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public Page<Transacao> findAllByUsuarioIdOrderByDataTransacaoDesc(Long usuarioId, Pageable paginacao, FiltroPaginacaoTransacao filtro) {
        Specification<TransacaoEntity> spec = TransacaoSpecification.filtrar(usuarioId, filtro);
        return jpaRepository
                .findAll(spec, paginacao)
                .map(entityMapper::toDomain);
    }

    @Override
    public Transacao save(Transacao conta) {
        TransacaoEntity entity = entityMapper.toEntity(conta);
        TransacaoEntity saved = jpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<Transacao> findById(Long id) {
        return jpaRepository
                .findById(id)
                .map(entityMapper::toDomain);
    }

    @Override
    public Transacao getReferenceById(Long id) {
        TransacaoEntity entity = jpaRepository.getReferenceById(id);
        return entityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void delete(Transacao transacao) {
        TransacaoEntity entity = entityMapper.toEntity(transacao);
        jpaRepository.delete(entity);
    }

}
