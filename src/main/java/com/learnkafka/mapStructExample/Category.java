package com.learnkafka.mapStructExample;

public class Category {

    protected String name;

    public Category(){

    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
