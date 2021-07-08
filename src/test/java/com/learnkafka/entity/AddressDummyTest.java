package com.learnkafka.entity;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AddressDummyTest {

    private Validator validator;

    @BeforeEach
    public void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateAddressValue(){
        AddressDummy addressDummy = new AddressDummy();
        addressDummy.setAddress("Noida");
        Set<ConstraintViolation<AddressDummy>> violations = validator.validate(addressDummy);
        assertFalse(violations.isEmpty());
    }

    @Test
    void validateAddressValue_valid(){
        AddressDummy addressDummy = new AddressDummy();
        addressDummy.setAddress("Sydney");
        Set<ConstraintViolation<AddressDummy>> violations = validator.validate(addressDummy);
        assertTrue(violations.isEmpty());
    }

}