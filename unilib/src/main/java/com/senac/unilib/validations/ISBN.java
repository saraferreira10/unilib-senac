package com.senac.unilib.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = IsbnValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {

    String message() default "O ISBN deve estar no formato XXX-XX-XXXXX-XX-X, tendo apenas números!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
