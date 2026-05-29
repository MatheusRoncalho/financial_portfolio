package com.roncalho.financial_portfolio.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;

public class OperacaoValorValidator implements ConstraintValidator<OperacaoValorObrigatoria, Object> {
    private String nomeValor;

    @Override
    public void initialize(OperacaoValorObrigatoria annotation) {
        this.nomeValor = annotation.nomeValor();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object valor = invocarMetodo(value, nomeValor);
        Object operacao = invocarMetodo(value, "operacaoValor");

        // Se tem valor mas não tem operacao
        if (valor != null && operacao == null) {
            context.buildConstraintViolationWithTemplate(
                    "Quando informar '" + nomeValor + "', 'operacaoValor' é obrigatória"
            ).addPropertyNode("operacaoValor").addConstraintViolation();
            return false;
        }

        // Se tem operacao mas não tem valor
        if (operacao != null && valor == null) {
            context.buildConstraintViolationWithTemplate(
                    "Quando informar 'operacaoValor', '" + nomeValor + "' é obrigatório"
            ).addPropertyNode(nomeValor).addConstraintViolation();
            return false;
        }

        return true;
    }

    private Object invocarMetodo(Object obj, String nomeMetodo) {
        try {
            Method metodo = obj.getClass().getMethod(nomeMetodo);
            return metodo.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }
}
