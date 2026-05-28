package com.roncalho.financial_portfolio.specification;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteFiltroRequestDTO;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoRecorrenteSpecification {

    public static Specification<TransacaoRecorrente> comFiltros(Long usuarioId, TransacaoRecorrenteFiltroRequestDTO filtro){
        return Specification.where(usuarioIdIgual(usuarioId))
                .and(descricaoContem(filtro.descricao()))
                .and(valorComOperacao(filtro.valor(), filtro.operacaoValor()))
                .and(tipoIgual(filtro.tipo()))
                .and(periodoIgual(filtro.periodo()))
                .and(statusIgual(filtro.status()))
                .and(dataTransacaoEntre(filtro.dataInicial(), filtro.dataFinal()))
                .and(categoriaIdIgual(filtro.categoriaId()));
    }

    private static Specification<TransacaoRecorrente> usuarioIdIgual(Long usuarioId) {
        return (root, query, cb) -> {
            if (usuarioId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("usuario").get("id"), usuarioId);
        };
    }

    public static Specification<TransacaoRecorrente> descricaoContem(String descricao){
        return ((root, query, cb) -> {
            if (descricao == null || descricao.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
        });
    }

    private static Specification<TransacaoRecorrente> valorComOperacao(BigDecimal valor, OperacaoComparacao operacao) {
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

    private static Specification<TransacaoRecorrente> tipoIgual(TipoTransacao tipo) {
        return (root, query, cb) -> {
            if (tipo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipo"), tipo);
        };
    }

    private static Specification<TransacaoRecorrente> periodoIgual(PeriodoRecorrencia periodo) {
        return ((root, query, cb) -> {
            if (periodo == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("periodo"), periodo);
        });
    }

    private static Specification<TransacaoRecorrente> statusIgual(StatusRecorrencia status) {
        return ((root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("status"), status);
        });
    }

    private static Specification<TransacaoRecorrente> dataTransacaoEntre(LocalDate dataInicial, LocalDate dataFinal) {
        return (root, query, cb) -> {
            if (dataInicial == null && dataFinal == null) {
                return cb.conjunction();
            }
            if (dataInicial != null && dataFinal == null) {
                return cb.greaterThanOrEqualTo(root.get("dataInicial"), dataInicial);
            }
            if (dataInicial == null && dataFinal != null) {
                return cb.lessThanOrEqualTo(root.get("dataInicial"), dataFinal);
            }
            return cb.between(root.get("dataInicial"), dataInicial, dataFinal);
        };
    }

    private static Specification<TransacaoRecorrente> categoriaIdIgual(Long categoriaId) {
        return (root, query, cb) -> {
            if (categoriaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("categoria").get("id"), categoriaId);
        };
    }
}
