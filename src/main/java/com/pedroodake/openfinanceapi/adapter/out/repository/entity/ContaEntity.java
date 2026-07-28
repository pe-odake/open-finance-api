package com.pedroodake.openfinanceapi.adapter.out.repository.entity;

import com.pedroodake.openfinanceapi.application.core.domain.enums.Banco;
import com.pedroodake.openfinanceapi.application.core.domain.enums.TipoConta;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Conta")
@Table(name = "contas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class ContaEntity {

    public ContaEntity(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @Enumerated(EnumType.STRING)
    private Banco nomeBanco;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    private BigDecimal saldo;
    private LocalDateTime ultimaSincronizacao;
    @CreationTimestamp
    @Column(name = "criada_em", updatable = false)
    private LocalDateTime criadaEm;
}
