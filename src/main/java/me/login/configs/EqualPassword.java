package me.login.configs;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.login.services.PasswordConstraintValidator;
import me.login.services.PasswordEqualValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordEqualValidator.class)
public @interface EqualPassword {
    String message() default "Passwörter stimmen nicht überein.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
