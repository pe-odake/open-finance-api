package com.pedroodake.openfinanceapi.application.core.domain.model;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;

import java.time.LocalDateTime;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private boolean ativo = true;
    private Perfil perfil;
    private LocalDateTime criado_em;
    private LocalDateTime atualizado_em;

    public Usuario(
            Long id,
            String nome,
            String login,
            String senha,
            boolean ativo,
            Perfil perfil,
            LocalDateTime criado_em,
            LocalDateTime atualizado_em) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.senha = senha;
        this.login = login;
        this.perfil = perfil;
        this.criado_em = criado_em;
        this.atualizado_em = atualizado_em;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Perfil getPerfil() { return perfil; }

    public LocalDateTime getCriado_em() { return criado_em; }

    public LocalDateTime getAtualizado_em() { return atualizado_em; }

    public void atualizarInformacoes(
            String nome,
            String login,
            Perfil perfil) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
        if (login != null && !login.isBlank()) {
            this.login = login;
        }
        if (perfil != null) {
            this.perfil = perfil;
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public void atualizarSenha(String senhaNovaCriptografada) {
        this.senha = senhaNovaCriptografada;
    }

}