package com.intuit.demo.vehiclerealtimedatareceiver.service.impl;

import com.intuit.demo.vehiclerealtimedatareceiver.common.mapper.VehicleMapper;
import com.intuit.demo.vehiclerealtimedatareceiver.dao.repository.VehicleEventsRepository;
import com.intuit.demo.vehiclerealtimedatareceiver.service.RegisteredVehicleService;
import com.intuit.demo.vehiclerealtimedatareceiver.service.dto.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
public class RoadVehicleService implements RegisteredVehicleService {

    private final VehicleEventsRepository vehicleEventsRepository;
    private final LocationService<Double, Double> locationService;

    public RoadVehicleService(VehicleEventsRepository vehicleEventsRepository, LocationService<Double, Double> locationService) {
        this.vehicleEventsRepository = vehicleEventsRepository;
        this.locationService = locationService;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        log.info("before saving the event in the database table");
        var distance = locationService.calculateDistance(Tuples.of(vehicle.getLatitude(), vehicle.getLongitude()),
                Tuples.of(vehicle.getLastKnowGeoLocation().getLatitude(), vehicle.getLastKnowGeoLocation().getLongitude()), vehicle.getSpeed());
        vehicle.setDistance(distance);

        var enty = VehicleMapper.INSTANCE.vehicleDtoToVehicleEntity(vehicle);
        enty.setId(UUID.randomUUID().toString());
        enty.setUpdateTs(LocalDateTime.now(ZoneId.of("UTC")));

        try {
            var en = vehicleEventsRepository.save(enty);
            log.info("saved the event in the database table {}", en);
            return VehicleMapper.INSTANCE.vehicleEntityToVehicleDto(en);
        } catch (Exception e) {
            log.error("exception thrown at database save {}", enty, e);
        }

        return null;
    }
}
