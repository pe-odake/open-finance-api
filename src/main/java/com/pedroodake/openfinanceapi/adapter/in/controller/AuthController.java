package com.pedroodake.openfinanceapi.adapter.in.controller;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosLogin;
import com.pedroodake.openfinanceapi.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import com.pedroodake.openfinanceapi.application.core.service.LoginService;
import com.pedroodake.openfinanceapi.application.core.service.UsuarioService;
import com.pedroodake.openfinanceapi.application.port.in.CadastroController;
import com.pedroodake.openfinanceapi.application.port.in.LoginPort;
import com.pedroodake.openfinanceapi.config.security.dto.DadosTokenJWT;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController implements
        LoginPort,
        CadastroController<DadosCadastroUsuario, DadosDetalhamentoUsuario> {

    private final LoginService service;
    private final UsuarioService usuarioService;

    public AuthController(
            LoginService service,
            UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> logar(
            @RequestBody @Valid DadosLogin dados) {
        return ResponseEntity.ok(service.logar(dados));
    }

    @Override
    @PostMapping("/registrar")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoUsuario dto = usuarioService.cadastrarUsuario(dados);
        URI uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(dto.id())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}