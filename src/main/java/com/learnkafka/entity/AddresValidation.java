package com.learnkafka.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddresValidation implements ConstraintValidator<AddresValidator,String> {

    List<String> address = Arrays.asList("Sydney");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return address.contains(value);
    }
}
