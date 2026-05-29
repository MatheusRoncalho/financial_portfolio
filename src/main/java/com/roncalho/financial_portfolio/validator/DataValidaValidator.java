package com.roncalho.financial_portfolio.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.time.LocalDate;

public class DataValidaValidator implements ConstraintValidator<DataValida, Object> {
    String nomeDataInicial;
    String nomeDataFinal;

    @Override
    public void initialize(DataValida annotation) {
        this.nomeDataInicial = annotation.nomeDataInicial();
        this.nomeDataFinal = annotation.nomeDataFinal();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate dataInicial = (LocalDate) invocarMetodo(value, nomeDataInicial);
        LocalDate dataFinal = (LocalDate) invocarMetodo(value, nomeDataFinal);

        if (dataInicial == null || dataFinal == null) {
            return true;
        }

        if (dataInicial.isAfter(dataFinal)) {
            context.buildConstraintViolationWithTemplate(
                    "Data inicial (" + dataInicial + ") não pode ser maior que data final (" + dataFinal + ")"
            ).addPropertyNode(nomeDataInicial).addConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Data final (" + dataFinal + ") não pode ser menor que data inicial (" + dataInicial + ")"
            ).addPropertyNode("dataFinal").addConstraintViolation();
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
