package com.roncalho.financial_portfolio.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OperacaoValorValidator.class)
@Documented
public @interface OperacaoValorObrigatoria {
    String nomeValor() default "valor";
    String message() default "Quando informar valor, operacaoValor é obrigatória";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
