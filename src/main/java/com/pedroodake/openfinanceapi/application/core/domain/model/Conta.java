package com.pedroodake.openfinanceapi.application.core.domain.model;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Banco;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoConta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Conta {
    private Long id;
    private Long usuarioId;
    private Banco nomeBanco;
    private TipoConta tipoConta;
    private BigDecimal saldo;
    private LocalDateTime ultimaSincronizacao;
    private LocalDateTime criadaEm;

    public Conta(
            Long id,
            Long usuarioId,
            Banco nomeBanco,
            TipoConta tipoConta,
            BigDecimal saldo,
            LocalDateTime ultimaSincronizacao,
            LocalDateTime criadaEm) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nomeBanco = nomeBanco;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.ultimaSincronizacao = ultimaSincronizacao;
        this.criadaEm = criadaEm;
    }

    public Long getId() { return id; }

    public Long getUsuarioId() { return usuarioId; }

    public Banco getNomeBanco() { return nomeBanco; }

    public TipoConta getTipoConta() { return tipoConta; }

    public BigDecimal getSaldo() { return saldo; }

    public LocalDateTime getUltimaSincronizacao() { return ultimaSincronizacao; }

    public LocalDateTime getCriadaEm() { return criadaEm; }

    public void delete(Conta conta) {

    }
}
