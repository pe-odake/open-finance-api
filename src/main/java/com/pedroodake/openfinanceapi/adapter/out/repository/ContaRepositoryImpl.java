package com.pedroodake.openfinanceapi.adapter.out.repository;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.ContaEntity;
import com.pedroodake.openfinanceapi.adapter.out.repository.mapper.ContaEntityMapper;
import com.pedroodake.openfinanceapi.adapter.out.repository.persistence.ContaJpaRepository;
import com.pedroodake.openfinanceapi.application.core.domain.model.Conta;
import com.pedroodake.openfinanceapi.application.port.out.ContaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ContaRepositoryImpl implements ContaRepository {
    private final ContaJpaRepository jpaRepository;
    private final ContaEntityMapper entityMapper;

    public ContaRepositoryImpl(
            ContaJpaRepository jpaRepository,
            ContaEntityMapper entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Conta> findAllByNomeBancoAsc() {
        return jpaRepository
                .findAllByOrderByNomeBancoAsc()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public Conta save(Conta conta) {
        ContaEntity entity = entityMapper.toEntity(conta);
        ContaEntity saved = jpaRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public Optional<Conta> findById(Long id) {
        return jpaRepository
                .findById(id)
                .map(entityMapper::toDomain);
    }

    @Override
    public Conta getReferenceById(Long id) {
        ContaEntity entity = jpaRepository.getReferenceById(id);
        return entityMapper.toDomain(entity);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void delete(Conta conta) {
        ContaEntity entity = entityMapper.toEntity(conta);
        jpaRepository.delete(entity);
    }
}
