package com.pedroodake.openfinanceapi.adapter.out.repository.persistence;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaJpaRepository extends JpaRepository<ContaEntity, Long> {
    List<ContaEntity> findAllByOrderByNomeBancoAsc();
}
