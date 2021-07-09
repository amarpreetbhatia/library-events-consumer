package com.learnkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDummy {
    @NotNull
    @AddresValidator
    private String address;

    @DateFormatValidator(format = "yyyy-MM-dd'T'HH:mm:ssX")
    private String addressDate;

    @DateFormatValidator(format = "yyyy-MM-dd",dateType= "LocalDate")
    private LocalDate addressLocalDate;
}
