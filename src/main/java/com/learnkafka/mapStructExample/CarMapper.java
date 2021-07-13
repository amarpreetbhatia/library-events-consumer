package com.learnkafka.mapStructExample;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {
    @Mapping(source = "seatCount", target = "numberOfSeats")
    @Mapping(source = "category", target = "category.name")
    @Mapping(source = "model", target = "model.name")
    Car convertToCar(CarDto carDto);
}
