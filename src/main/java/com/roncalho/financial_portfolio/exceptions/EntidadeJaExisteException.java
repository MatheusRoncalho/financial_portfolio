package com.roncalho.financial_portfolio.exceptions;

public class EntidadeJaExisteException extends RuntimeException {
    public EntidadeJaExisteException(String message) {
        super(message);
    }
}

