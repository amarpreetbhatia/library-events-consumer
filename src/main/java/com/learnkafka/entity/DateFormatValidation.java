package com.learnkafka.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatValidation implements ConstraintValidator<DateFormatValidator,Object> {
    private String format;
    private String dateType;

    @Override
    public void initialize(DateFormatValidator constraintAnnotation) {
       this.format = constraintAnnotation.format();
       this.dateType = constraintAnnotation.dateType();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
       if(dateType.equalsIgnoreCase("LocalDate")){
           return isValidLocalDate(format,(LocalDate)value);
       }else {
           return isValidStringDate(format, (String) value);
       }
    }

    private boolean isValidStringDate(String format,String value) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isValidLocalDate(String format, LocalDate value) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            value.format(dateTimeFormatter);
        }catch (Exception e){
            return false;
        }
        return true;

//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat(format);
//            sdf.format(value);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
    }
}
