package com.intuit.demo.vehiclerealtimedatareceiver.dao.repository;

import com.intuit.demo.vehiclerealtimedatareceiver.dao.entity.VehicleEventsEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleEventsRepository extends CassandraRepository<VehicleEventsEntity, String> {
}
