package com.roncalho.financial_portfolio.specification;

import com.roncalho.financial_portfolio.dto.in.TransacaoRecorrenteFiltroRequestDTO;
import com.roncalho.financial_portfolio.enums.OperacaoComparacao;
import com.roncalho.financial_portfolio.enums.PeriodoRecorrencia;
import com.roncalho.financial_portfolio.enums.StatusRecorrencia;
import com.roncalho.financial_portfolio.enums.TipoTransacao;
import com.roncalho.financial_portfolio.model.TransacaoRecorrente;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class TransacaoRecorrenteSpecification {

    public static Specification<TransacaoRecorrente> comFiltros(Long usuarioId, TransacaoRecorrenteFiltroRequestDTO filtro){
        return Specification.where(TransacaoRecorrenteSpecification.usuarioIdIgual(usuarioId))
                .and(TransacaoRecorrenteSpecification.descricaoContem(filtro.descricao()))
                .and(TransacaoRecorrenteSpecification.valorComOperacao(filtro.valor(), filtro.operacaoValor()))
                .and(TransacaoRecorrenteSpecification.tipoIgual(filtro.tipo()))
                .and(TransacaoRecorrenteSpecification.periodoIgual(filtro.periodo()))
                .and(TransacaoRecorrenteSpecification.statusIgual(filtro.status()))
                .and(TransacaoRecorrenteSpecification.dataTransacaoEntre(filtro.dataInicial(), filtro.dataFinal()))
                .and(TransacaoRecorrenteSpecification.categoriaIdIgual(filtro.categoriaId()));
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

    private static Specification<TransacaoRecorrente> dataTransacaoEntre(String dataInicio, String dataFinal) {
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

    private static Specification<TransacaoRecorrente> categoriaIdIgual(Long categoriaId) {
        return (root, query, cb) -> {
            if (categoriaId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipo"), categoriaId);
        };
    }
}
