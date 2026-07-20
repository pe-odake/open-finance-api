package com.pedroodake.openfinanceapi.application.core.service;

import com.pedroodake.openfinanceapi.adapter.in.controller.request.usuario.DadosLogin;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.UsuarioEntity;
import com.pedroodake.openfinanceapi.adapter.out.repository.mapper.UsuarioEntityMapper;
import com.pedroodake.openfinanceapi.adapter.out.repository.persistence.UsuarioJpaRepository;
import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import com.pedroodake.openfinanceapi.config.security.dto.DadosTokenJWT;
import com.pedroodake.openfinanceapi.config.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioEntityMapper entityMapper;
    private final UsuarioJpaRepository jpaRepository;

    public LoginService(
            AuthenticationManager manager,
            TokenService tokenService,
            UsuarioEntityMapper entityMapper,
            UsuarioJpaRepository jpaRepository) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.entityMapper = entityMapper;
        this.jpaRepository = jpaRepository;
    }

    public DadosTokenJWT logar(DadosLogin dados) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                dados.login(),
                dados.senha()
        );
        Authentication authentication = manager.authenticate(token);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = userDetails.getUsername();
        UsuarioEntity entity = (UsuarioEntity) jpaRepository.findByLogin(login);
        Usuario usuario = entityMapper.toDomain(entity);
        String tokenJWT = tokenService.gerarToken(usuario);
        return new DadosTokenJWT(
                tokenJWT,
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPerfil()
        );
    }
}