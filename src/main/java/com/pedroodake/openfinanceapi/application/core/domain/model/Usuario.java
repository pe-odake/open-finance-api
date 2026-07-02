package com.pedroodake.openfinanceapi.application.core.domain.model;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Perfil;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private boolean ativo = true;
    private Perfil perfil;

    public Usuario(
            Long id,
            String nome,
            String login,
            String senha,
            boolean ativo,
            Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.senha = senha;
        this.login = login;
        this.perfil = perfil;
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

    public Perfil getPerfil() {
        return perfil;
    }

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