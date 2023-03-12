package me.login.services;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.login.configs.EqualPassword;
import me.login.models.IdentificationDataDto;

public class PasswordEqualValidator implements ConstraintValidator<EqualPassword, IdentificationDataDto> {

    @Override
    public void initialize(EqualPassword arg0) {}

    @Override
    public boolean isValid(IdentificationDataDto identificationDataDto, ConstraintValidatorContext context) {
        return identificationDataDto.getPassword().equals(identificationDataDto.getPasswordConfirmed());
    }
}