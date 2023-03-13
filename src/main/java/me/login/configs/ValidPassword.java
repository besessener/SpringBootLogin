package me.login.configs;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import me.login.services.PasswordConstraintValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({PARAMETER, TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "Password invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
