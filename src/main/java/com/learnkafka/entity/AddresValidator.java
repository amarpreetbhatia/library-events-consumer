package com.learnkafka.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AddresValidation.class)
public @interface AddresValidator {
    String message() default "This is not a valid value of Address";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
