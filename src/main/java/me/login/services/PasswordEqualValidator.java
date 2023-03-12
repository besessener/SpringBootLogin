package me.login.services;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import me.login.configs.EqualPassword;
import me.login.configs.ValidPassword;
import me.login.models.IdentificationDataDto;
import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordEqualValidator implements ConstraintValidator<EqualPassword, IdentificationDataDto> {

    @Override
    public void initialize(EqualPassword arg0) {}

    @Override
    public boolean isValid(IdentificationDataDto identificationDataDto, ConstraintValidatorContext context) {
        return identificationDataDto.getPassword().equals(identificationDataDto.getPasswordConfirmed());
    }
}