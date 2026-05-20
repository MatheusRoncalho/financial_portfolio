package com.roncalho.financial_portfolio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum TipoTransacao {
    ENTRADA(1, "Entrada"),
    SAIDA(2, "Saida"),
    INVESTIMENTO(3, "Investimento");

    private final int valor;
    private final String descricao;

    public static TipoTransacao fromValor(int valor) {
        for (TipoTransacao tipo : values()) {
            if (tipo.getValor() == valor) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor de tipo transação inválido: " + valor);
    }

    public static TipoTransacao fromTexto(String texto) {
        return switch (texto.toLowerCase()) {
            case "gastei", "paguei", "comprei", "saida", "gasto" -> SAIDA;
            case "recebi", "ganhei", "entrada", "renda" -> ENTRADA;
            default -> throw new IllegalArgumentException("Tipo não reconhecido: " + texto);
        };
    }
}
