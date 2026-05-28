package com.roncalho.financial_portfolio.specification;

import com.roncalho.financial_portfolio.dto.in.MetaFiltroRequestDTO;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.model.Meta;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaSpecification {

    public static Specification<Meta> comFiltros(Long usuarioId, MetaFiltroRequestDTO filtro) {
        return Specification.where(usuarioIdIgual(usuarioId))
                .and(categoriaIdIgual(filtro.categoriaId()))
                .and(valorComOperacao(filtro.valorLimite(), filtro.operacaoValor()))
                .and(dataEntre(filtro.dataInicio(), filtro.dataFim()));
    }

    private static Specification<Meta> usuarioIdIgual(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    private static Specification<Meta> categoriaIdIgual(Long categoriaId) {
        return (root, query, cb) -> {
            if (categoriaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("categoria").get("id"), categoriaId);
        };
    }

    private static Specification<Meta> valorComOperacao(BigDecimal valorLimite, OperacaoComparacao operacao) {
        return (root, query, cb) -> {
            if (valorLimite == null || operacao == null) {
                return cb.conjunction();
            }

            return switch (operacao) {
                case IGUAL -> cb.equal(root.get("valorLimite"), valorLimite);
                case MAIOR_QUE -> cb.greaterThan(root.get("valorLimite"), valorLimite);
                case MENOR_QUE -> cb.lessThan(root.get("valorLimite"), valorLimite);
                case MAIOR_OU_IGUAL -> cb.greaterThanOrEqualTo(root.get("valorLimite"), valorLimite);
                case MENOR_OU_IGUAL -> cb.lessThanOrEqualTo(root.get("valorLimite"), valorLimite);
                default -> cb.conjunction();
            };
        };
    }

    private static Specification<Meta> dataEntre(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, cb) -> {
            if (dataInicio == null && dataFim == null) {
                return cb.conjunction();
            }
            if (dataInicio != null && dataFim == null) {
                return cb.greaterThanOrEqualTo(root.get("dataInicio"), dataInicio);
            }
            if (dataInicio == null && dataFim != null) {
                return cb.lessThanOrEqualTo(root.get("dataInicio"), dataFim);
            }
            return cb.between(root.get("dataInicio"), dataInicio, dataFim);
        };
    }
}
