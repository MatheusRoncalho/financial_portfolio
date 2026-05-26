package com.roncalho.financial_portfolio.enums;

public enum OperacaoComparacao {
    IGUAL("="),
    MAIOR_QUE(">"),
    MENOR_QUE("<"),
    MAIOR_OU_IGUAL(">="),
    MENOR_OU_IGUAL("<=");

    private final String simbolo;

    OperacaoComparacao(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}