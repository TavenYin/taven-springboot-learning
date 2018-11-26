package com.gitee.taven.validate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || "".equals(value.trim())) {
            return false;
        }

        switch (this.caseMode) {
            case LOWER:
                return value.equals(value.toLowerCase());
            case UPPER:
                return value.equals(value.toUpperCase());
            default:
                return false;
        }

    }

}

