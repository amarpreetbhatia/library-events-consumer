package com.learnkafka.entity;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    void validateAddressDateValue_valid(){
        AddressDummy addressDummy = new AddressDummy();
        addressDummy.setAddress("Sydney");
        addressDummy.setAddressDate("sssss");
        //yyyy-MM-dd
        addressDummy.setAddressLocalDate(LocalDate.parse("2016-07-07"));
        Set<ConstraintViolation<AddressDummy>> violations = validator.validate(addressDummy);
        assertFalse(violations.isEmpty());
        violations.forEach(e -> {
            assertEquals("This is not a valid date format",e.getMessage());
        });
    }

    @Test
    void validateAddressDateValue_valid2(){
        AddressDummy addressDummy = new AddressDummy();
        addressDummy.setAddress("Sydney");
        addressDummy.setAddressDate("2021-01-16T20:00:00+10:00");
        //yyyy-MM-dd
        addressDummy.setAddressLocalDate(LocalDate.parse("2016-01-01"));
        Set<ConstraintViolation<AddressDummy>> violations = validator.validate(addressDummy);
        assertTrue(violations.isEmpty());
    }

    @Test
    void zoneDateTime_main() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2011-12-03T10:15:30+01:00");
        System.out.println("----------------------------");
        System.out.println(Date.from(zonedDateTime.toInstant()));
        System.out.println("----------------------------");
    }

}