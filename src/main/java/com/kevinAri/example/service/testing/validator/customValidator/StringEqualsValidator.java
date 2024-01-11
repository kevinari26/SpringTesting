package com.kevinAri.example.service.testing.validator.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringEqualsValidator implements ConstraintValidator<StringEquals, String> {
    public static final String ACTION = "action";
    private String flag;

    public void initialize(StringEquals annotation) {
        flag = annotation.flag();
    }

    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        if (flag.equals(ACTION)) {
            if ("REQUEST".equals(field)) return true;
            else return false;
        }
        return true;
    }
}
