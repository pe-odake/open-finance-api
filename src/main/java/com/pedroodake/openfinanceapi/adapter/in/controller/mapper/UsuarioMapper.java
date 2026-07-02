package com.pedroodake.openfinanceapi.adapter.in.controller.mapper;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosListagemUsuario;
import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toDomain(DadosCadastroUsuario dados) {
        return new Usuario(
                null,
                dados.nome(),
                dados.login(),
                dados.senha(), // AQUI
                true,
                dados.perfil()
        );
    }

    public DadosListagemUsuario toListDTO(Usuario usuario) {
        return new DadosListagemUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.isAtivo(),
                usuario.getPerfil()
        );
    }

    public DadosDetalhamentoUsuario toDetailsDTO(Usuario usuario) {
        return new DadosDetalhamentoUsuario(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.isAtivo(),
                usuario.getPerfil()
        );
    }
}