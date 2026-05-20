package com.roncalho.financial_portfolio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum PeriodoRecorrencia {
    DIARIA(1, "Diária"),
    SEMANAL(2, "Semanal"),
    MENSAL(3, "Mensal"),
    ANUAL(4, "Anual");

    private final int valor;
    private final String descricao;

    public static PeriodoRecorrencia fromValor(int valor) {
        for (PeriodoRecorrencia periodo : values()) {
            if (periodo.getValor() == valor) {
                return periodo;
            }
        }
        throw new IllegalArgumentException("Valor de período recorrência inválido: " + valor);
    }

    public static PeriodoRecorrencia fromTexto(String texto) {
        return switch (texto.toLowerCase()) {
            case "diaria", "diário", "dia", "daily" -> DIARIA;
            case "semanal", "semana", "weekly" -> SEMANAL;
            case "mensal", "mês", "mes", "monthly" -> MENSAL;
            case "anual", "ano", "yearly" -> ANUAL;
            default -> throw new IllegalArgumentException("Período não reconhecido: " + texto);
        };
    }
}

