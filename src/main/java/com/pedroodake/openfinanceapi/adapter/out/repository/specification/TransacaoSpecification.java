package com.pedroodake.openfinanceapi.adapter.out.repository.specification;

import com.pedroodake.openfinanceapi.adapter.in.controller.response.transacao.FiltroPaginacaoTransacao;
import com.pedroodake.openfinanceapi.adapter.out.repository.entity.TransacaoEntity;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Banco;
import com.pedroodake.openfinanceapi.application.core.domain.enums.Categoria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransacaoSpecification {

    public static Specification<TransacaoEntity> filtrar(Long usuarioId, FiltroPaginacaoTransacao filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("conta").get("usuario").get("id"), usuarioId));

            if (filtro != null) {
                if (filtro.descricao() != null && !filtro.descricao().isBlank()) {
                    predicates.add(
                        cb.like(
                            cb.lower(root.get("descricao")),
                            "%" + filtro.descricao().trim().toLowerCase() + "%"
                        )
                    );
                }

                if (filtro.nomeBanco() != null && !filtro.nomeBanco().isBlank()) {
                    try {
                        Banco bancoEnum = Banco.valueOf(filtro.nomeBanco().trim().toUpperCase());
                        predicates.add(cb.equal(root.get("conta").get("nomeBanco"), bancoEnum));
                    } catch (IllegalArgumentException e) {
                         predicates.add(cb.disjunction());
                    }
                }

                if (filtro.categoria() != null && !filtro.categoria().isBlank()) {
                    try {
                        Categoria categoriaEnum = Categoria.valueOf(filtro.categoria().trim().toUpperCase());
                        predicates.add(cb.equal(root.get("categoria"), categoriaEnum));
                    } catch (IllegalArgumentException e) {
                        predicates.add(cb.disjunction());
                    }
                }

            }

            query.orderBy(cb.desc(root.get("dataTransacao")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
