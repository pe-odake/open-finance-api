package com.pedroodake.openfinanceapi.adapter.out.repository.mapper;

import com.pedroodake.openfinanceapi.adapter.out.repository.entity.UsuarioEntity;
import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {
    public Usuario toDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                entity.getLogin(),
                entity.getSenha(),
                entity.isAtivo(),
                entity.getPerfil()
        );
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.isAtivo(),
                usuario.getPerfil()
        );
    }
}