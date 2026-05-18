package com.roncalho.financial_portfolio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum PeriodoMeta {
    MENSAL(1, "Mensal"),
    ANUAL(2, "Anual");

    private final int valor;
    private final String descricao;

    public static PeriodoMeta fromValor(int valor) {
        for (PeriodoMeta periodo : values()) {
            if (periodo.getValor() == valor) {
                return periodo;
            }
        }
        throw new IllegalArgumentException("Valor de período meta inválido: " + valor);
    }

    public static PeriodoMeta fromTexto(String texto) {
        return switch (texto.toLowerCase()) {
            case "mensal", "mês", "mes" -> MENSAL;
            case "anual", "ano" -> ANUAL;
            default -> throw new IllegalArgumentException("Período não reconhecido: " + texto);
        };
    }
}

