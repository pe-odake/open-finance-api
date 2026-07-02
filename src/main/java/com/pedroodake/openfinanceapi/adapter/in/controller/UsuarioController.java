package com.pedroodake.openfinanceapi.adapter.in.controller;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosAtualizacaoSenha;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosAtualizacaoUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosListagemUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosSucesso;
import com.pedroodake.openfinanceapi.application.core.service.UsuarioService;
import com.pedroodake.openfinanceapi.application.port.in.ModelDomainController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController implements ModelDomainController<
        DadosCadastroUsuario,
        DadosListagemUsuario,
        DadosAtualizacaoUsuario,
        Void,
        DadosDetalhamentoUsuario,
        Long> {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoUsuario dto = service.cadastrarUsuario(dados);
        URI uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Page<DadosListagemUsuario>> listar(
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.listarUsuarios(paginacao));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoUsuario> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.detalharUsuario(id));
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
            @RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return ResponseEntity.ok(service.atualizarUsuario(dados));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosSucesso> atualizarSenha(
            @RequestBody @Valid DadosAtualizacaoSenha dados) {
        return ResponseEntity.ok(service.atualizarSenhaUsuario(dados));
    }
}