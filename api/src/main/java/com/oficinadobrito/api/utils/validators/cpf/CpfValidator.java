package com.oficinadobrito.api.utils.validators.cpf;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<CPF, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return false;
        }
        return isValidCPF(cpf);
    }

    private boolean isValidCPF(String cpf) {
        return CPFUtils.isValidCPF(cpf);
    }
}
