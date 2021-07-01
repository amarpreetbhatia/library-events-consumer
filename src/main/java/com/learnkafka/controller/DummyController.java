package com.learnkafka.controller;

import com.learnkafka.config.BasicConfigurationProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DummyController {

    @Autowired
    BasicConfigurationProperties basicConfigurationProperties;

    @Operation(summary = "This to get the basic value from Property files")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",description=" Get a String Value")
            }
    )
    @GetMapping("/basic/values")
    public String showBasicPropertyValue(){
        log.info("Printing basic value1 {} that was written on {}",
                basicConfigurationProperties.getValue1(),
                basicConfigurationProperties.getBasicDate());
        return basicConfigurationProperties.getValue1();
    }
}
