package com.roncalho.financial_portfolio.specification;

import com.roncalho.financial_portfolio.dto.in.TransacaoFiltroRequestDTO;

import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.Transacao;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class TransacaoSpecification {

    public static Specification<Transacao> comFiltros(Long usuarioId, TransacaoFiltroRequestDTO filtro){
        return Specification.where(usuarioIdIgual(usuarioId))
                .and(descricaoContem(filtro.descricao()))
                .and(valorComOperacao(filtro.valor(), filtro.operacaoValor()))
                .and(tipoIgual(filtro.tipo()))
                .and(dataTransacaoEntre(filtro.dataTransacao(), filtro.dataFinal()))
                .and(categoriaIdIgual(filtro.categoria()));
    }

    private static Specification<Transacao> usuarioIdIgual(Long usuarioId) {
        {
            return (root, query, cb) -> {
                if (usuarioId == null) {
                    return cb.conjunction();
                }
                return cb.equal(root.get("usuario").get("id"), usuarioId);
            };
        }
    }

    private static Specification<Transacao> descricaoContem(String descricao) {
        return (root, query, cb) -> {;
            if (descricao == null || descricao.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
        };
    }

    private static Specification<Transacao> valorComOperacao(BigDecimal valor, OperacaoComparacao operacao) {
        return (root, query, cb) -> {
            if (valor == null || operacao == null) {
                return cb.conjunction();
            }

            return switch (operacao) {
                case IGUAL -> cb.equal(root.get("valor"), valor);
                case MAIOR_QUE -> cb.greaterThan(root.get("valor"), valor);
                case MENOR_QUE -> cb.lessThan(root.get("valor"), valor);
                case MAIOR_OU_IGUAL -> cb.greaterThanOrEqualTo(root.get("valor"), valor);
                case MENOR_OU_IGUAL -> cb.lessThanOrEqualTo(root.get("valor"), valor);
                default -> cb.conjunction();
            };
        };
    }

    private static Specification<Transacao> tipoIgual(TipoTransacao tipo) {
        return (root, query, cb) -> {
            if (tipo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipo"), tipo);
        };
    }

    private static Specification<Transacao> dataTransacaoEntre(String dataInicio, String dataFinal) {
        return (root, query, cb) -> {
            if (dataInicio == null && dataFinal == null) {
                return cb.conjunction();
            }
            if (dataInicio != null && dataFinal == null) {
                return cb.greaterThanOrEqualTo(root.get("dataTransacao"), dataInicio);
            }
            if (dataInicio == null && dataFinal != null) {
                return cb.lessThanOrEqualTo(root.get("dataTransacao"), dataFinal);
            }
            return cb.between(root.get("dataTransacao"), dataInicio, dataFinal);
        };
    }

    private static Specification<Transacao> categoriaIdIgual(Long categoriaId) {
        return (root, query, cb) -> {
            if (categoriaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipo"), categoriaId);
        };
    }
}

