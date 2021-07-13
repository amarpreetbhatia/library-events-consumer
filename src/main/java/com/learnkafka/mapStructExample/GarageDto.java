package com.learnkafka.mapStructExample;

import java.util.List;

public class GarageDto {
    protected String location;
    protected List<CarDto> cars;

    public GarageDto(String location, List<CarDto> cars) {
        this.location = location;
        this.cars = cars;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }
}
