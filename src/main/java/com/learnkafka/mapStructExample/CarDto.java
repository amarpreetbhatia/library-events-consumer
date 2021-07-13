package com.learnkafka.mapStructExample;

public class CarDto {
    protected String make;
    protected int seatCount;
    protected String manufacturingDate;
    protected double price;
    protected String category;

    protected String model;

    public CarDto(String make, int seatCount, String manufacturingDate, double price, String category, String model) {
        this.make = make;
        this.seatCount = seatCount;
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

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
