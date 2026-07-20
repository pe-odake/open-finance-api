package com.pedroodake.openfinanceapi.adapter.in.controller;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.conta.DadosCadastroConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosDetalhamentoConta;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.conta.DadosListagemConta;
import com.pedroodake.openfinanceapi.application.core.service.ContaService;
import com.pedroodake.openfinanceapi.application.port.in.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contas")
@SecurityRequirement(name = "bearer-key")
public class ContaController implements
        CadastroController<DadosCadastroConta, DadosDetalhamentoConta>,
        ListagemController<DadosListagemConta>,
        ExclusaoController<Void, Long>,
        DetalhamentoController<DadosDetalhamentoConta, Long> {
    private final ContaService service;

    public ContaController(ContaService service) { this.service = service; }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoConta> cadastrar(
            @RequestBody @Valid DadosCadastroConta dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoConta dto = service.cadastrarConta(dados);
        URI uri = uriBuilder
                .path("/contas/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Page<DadosListagemConta>> listar(Pageable paginacao) {
        List<DadosListagemConta> contas = service.listarContas();
        return ResponseEntity.ok(new org.springframework.data.domain.PageImpl<>(contas));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<DadosDetalhamentoConta> detalhar(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.detalharConta(id));
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEMO', 'DEFAULT')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirConta(id);
        return ResponseEntity.noContent().build();
    }
}
