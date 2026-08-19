package com.pedroodake.openfinanceapi.adapter.in.controller;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.transacao.DadosCadastroTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosDetalhamentoTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.DadosListagemTransacao;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.FiltroPaginacaoTransacao;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.UsuarioEntity;
import com.pedroodake.openfinanceapi.application.core.service.TransacaoService;
import com.pedroodake.openfinanceapi.application.port.in.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
@SecurityRequirement(name = "bearer-key")
public class TransacaoController implements
        CadastroController<DadosCadastroTransacao, DadosDetalhamentoTransacao>,
        ListagemFiltradaController<DadosListagemTransacao, FiltroPaginacaoTransacao>,
        ExclusaoController<Void, Long>,
        DetalhamentoController<DadosDetalhamentoTransacao, Long> {
    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoTransacao> cadastrar(
            @RequestBody @Valid DadosCadastroTransacao dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoTransacao dto = service.cadastrarTransacao(dados);
        URI uri = uriBuilder
                .path("/transacoes/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Page<DadosListagemTransacao>> listar(
            Pageable paginacao,
            FiltroPaginacaoTransacao filtro,
            @AuthenticationPrincipal(expression = "id") Long id) {
        Page<DadosListagemTransacao> pagina = service.listarTransacoes(id, paginacao, filtro);
        return ResponseEntity.ok(pagina);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoTransacao> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.detalharTransacao(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirTransacao(id);
        return ResponseEntity.noContent().build();
    }
}