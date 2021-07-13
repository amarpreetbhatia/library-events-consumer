package com.learnkafka.mapStructExample;

import java.util.List;

public class Garage {
    protected String location;
    protected List<Car> cars;

    public Garage(String location, List<Car> cars) {
        this.location = location;
        this.cars = cars;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
