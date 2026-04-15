package com.roncalho.financial_portfolio.model;

public enum TipoTransacao {
    ENTRADA,
    SAIDA;

    public static TipoTransacao fromTexto(String texto) {
        return switch (texto.toLowerCase()) {
            case "gastei", "paguei", "comprei" -> SAIDA;
            case "recebi", "ganhei"            -> ENTRADA;
            default -> throw new IllegalArgumentException("Tipo não reconhecido: " + texto);
        };
    }
}
