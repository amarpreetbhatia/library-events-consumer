package com.learnkafka.mapStructExample;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Car {
    protected String make;
    protected int numberOfSeats;
    protected LocalDate manufacturingDate;
    protected BigDecimal price;
    protected Category category;

    protected Model model;

    public Car(String make, int numberOfSeats, LocalDate manufacturingDate, BigDecimal price, Category category, Model model) {
        this.make = make;
        this.numberOfSeats = numberOfSeats;
        this.manufacturingDate = manufacturingDate;
        this.price = price;
        this.category = category;
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
