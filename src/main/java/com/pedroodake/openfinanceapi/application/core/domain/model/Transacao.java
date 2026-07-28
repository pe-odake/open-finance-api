package com.pedroodake.openfinanceapi.application.core.domain.model;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Categoria;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private Long id;
    private Long contaId;
    private String nomeBanco;
    private String tipoConta;
    private String descricao;
    private BigDecimal valor;
    private Categoria categoria;
    private TipoTransacao tipo;
    private LocalDateTime dataTransacao;

    public Transacao(
            Long id,
            Long contaId,
            String nomeBanco,
            String tipoConta,
            String descricao,
            BigDecimal valor,
            Categoria categoria,
            TipoTransacao tipo,
            LocalDateTime dataTransacao) {
        this.id = id;
        this.contaId = contaId;
        this.nomeBanco = nomeBanco;
        this.tipoConta = tipoConta;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.tipo = tipo;
        this.dataTransacao = dataTransacao;
    }

    public Long getId() { return id; }

    public Long getContaId() { return contaId; }

    public String getNomeBanco() { return nomeBanco; }

    public String getTipoConta() { return tipoConta; }

    public String getDescricao() { return descricao; }

    public BigDecimal getValor() { return valor; }

    public Categoria getCategoria() { return categoria; }

    public TipoTransacao getTipo() { return tipo; }

    public LocalDateTime getDataTransacao() { return dataTransacao; }
}