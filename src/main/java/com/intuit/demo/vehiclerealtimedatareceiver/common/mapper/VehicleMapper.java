package com.intuit.demo.vehiclerealtimedatareceiver.common.mapper;

import com.intuit.demo.vehiclerealtimedatareceiver.dao.entity.VehicleEventsEntity;
import com.intuit.demo.vehiclerealtimedatareceiver.service.dto.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    @Mapping(source = "timestamp", target = "eventTs")
    VehicleEventsEntity vehicleDtoToVehicleEntity(Vehicle vehicle);
    Vehicle vehicleEntityToVehicleDto(VehicleEventsEntity vehicleEventsEntity);
}
