package com.pedroodake.openfinanceapi.adapter.out.repository.mapper;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.ContaEntity;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.UsuarioEntity;
import com.pedroodake.openfinanceapi.application.core.domain.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaEntityMapper {
    public Conta toDomain(ContaEntity entity) {
        return new Conta(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getNomeBanco(),
                entity.getTipoConta(),
                entity.getSaldo(),
                entity.getUltimaSincronizacao(),
                entity.getCriadaEm()
        );
    }

    public ContaEntity toEntity(Conta conta) {
        return new ContaEntity(
                conta.getId(),
                new UsuarioEntity(conta.getUsuarioId()),
                conta.getNomeBanco(),
                conta.getTipoConta(),
                conta.getSaldo(),
                conta.getUltimaSincronizacao(),
                conta.getCriadaEm()
        );
    }
}
