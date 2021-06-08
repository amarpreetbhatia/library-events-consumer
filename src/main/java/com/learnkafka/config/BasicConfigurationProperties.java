package com.learnkafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("basic")
public class BasicConfigurationProperties {
    private String value1;
    private String basicDate;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getBasicDate() {
        return basicDate;
    }

    public void setBasicDate(String basicDate) {
        this.basicDate = basicDate;
    }
}
