package com.learnkafka.mapStructExample;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

// CarMapper uses , List of Car and CarDto is in Garage
@Mapper(uses = {CarMapper.class}, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GarageMapper {
    Garage map(GarageDto garageDto);
}
