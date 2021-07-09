package com.learnkafka.entity;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateFormatValidation.class)
public @interface DateFormatValidator {
    String message() default "This is not a valid date format";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String format() default "yyyy-MM-dd'T'HH:mm:ssZ";
    String dateType() default "String";
}
