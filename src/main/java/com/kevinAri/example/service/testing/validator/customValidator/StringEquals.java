package com.kevinAri.example.service.testing.validator.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringEqualsValidator.class)
public @interface StringEquals {
    String message() default "Invalid string equals";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String flag();
}
