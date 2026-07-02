package com.pedroodake.openfinanceapi.application.core.service;

import com.pedroodake.openfinanceapi.adapter.in.controller.mapper.UsuarioMapper;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosAtualizacaoSenha;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosAtualizacaoUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosListagemUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosSucesso;
import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import com.pedroodake.openfinanceapi.application.port.out.UsuarioRepository;
import com.pedroodake.openfinanceapi.exception.type.usuario.UsuarioNotFoundException;
import com.pedroodake.openfinanceapi.exception.type.usuario.SenhaIncorretaException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final UsuarioMapper mapper;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder encoder,
            UsuarioMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Transactional
    public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados) {
        String senhaCriptografada = encoder.encode(dados.senha());
        DadosCadastroUsuario dadosComSenhaHash = new DadosCadastroUsuario(
                dados.nome(),
                dados.login(),
                senhaCriptografada,
                dados.perfil()
        );
        Usuario usuario = mapper.toDomain(dadosComSenhaHash);
        Usuario saved = repository.save(usuario);
        return mapper.toDetailsDTO(saved);
    }

    public Page<DadosListagemUsuario> listarUsuarios(Pageable paginacao) {
        return repository
                .findAllByAtivoTrue(paginacao)
                .map(mapper::toListDTO);
    }

    public DadosDetalhamentoUsuario detalharUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException("ID do usuário informado não existe!"));
        return mapper.toDetailsDTO(usuario);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarUsuario(DadosAtualizacaoUsuario dados) {
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(() ->
                        new UsuarioNotFoundException("ID do usuário informado não existe!"));
        usuario.atualizarInformacoes(dados.nome(), dados.login(), dados.perfil());
        Usuario saved = repository.save(usuario);
        return mapper.toDetailsDTO(saved);
    }

    @Transactional
    public void excluirUsuario(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException("ID do usuário informado não existe!"));
        usuario.excluir();
        repository.save(usuario);
    }

    @Transactional
    public DadosSucesso atualizarSenhaUsuario(DadosAtualizacaoSenha dados) {
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(() ->
                        new UsuarioNotFoundException("ID do usuário informado não existe!"));
        if (!encoder.matches(dados.senhaAntiga(), usuario.getSenha())) {
            throw new SenhaIncorretaException("Senha incorreta!");
        }
        String senhaNovaCriptografada = encoder.encode(dados.senhaNova());
        usuario.atualizarSenha(senhaNovaCriptografada);
        repository.save(usuario);
        return new DadosSucesso("Senha atualizada com sucesso!");
    }
}