package com.pedroodake.openfinanceapi.adapter.out.repository.entity;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Categoria;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Transacao")
@Table(name = "transacao")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class TransacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private ContaEntity conta;

    private String descricao;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(name = "data_transacao", updatable = false)
    private LocalDateTime dataTransacao;
}
