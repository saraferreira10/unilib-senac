package com.senac.unilib.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public void initialize(ISBN constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        return value.matches("\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d");
    }
}
