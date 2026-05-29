package com.roncalho.financial_portfolio.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DataValidaValidator.class)
@Documented
public @interface DataValida {
    String nomeDataInicial() default "dataInicial";
    String nomeDataFinal()  default "dataFinal";
    String message() default "Data inicial deve ser menor que a data final";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
