package com.roncalho.financial_portfolio.specification;

import com.roncalho.financial_portfolio.dto.in.CategoriaFiltroRequestDTO;
import com.roncalho.financial_portfolio.model.Categoria;
import org.springframework.data.jpa.domain.Specification;

public class CategoriaSpecification {

    public static Specification<Categoria> comFiltros(Long usuarioId, CategoriaFiltroRequestDTO filtro) {
        return Specification.where(usuarioIdIgual(usuarioId))
                .and(nomecontem(filtro.nome()))
                .and(sistemaIgual(filtro.sistema()));
    }

    private static Specification<Categoria> usuarioIdIgual(Long usuarioId) {
        return (root, query, cb) -> {
            return cb.or(
                    cb.equal(root.get("usuario").get("id"), usuarioId),
                    cb.and(
                            cb.isNull(root.get("usuario")),
                            cb.isTrue(root.get("sistema"))
                    )
            );
        };
    }

    private static Specification<Categoria> nomecontem(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nome")), "%" + nome + "%");
        };
    }

    private static Specification<Categoria> sistemaIgual(Boolean sistema) {
        return (root, query, cb) -> {
            if (sistema == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("sistema"), sistema);
        };
    }
}
