package com.intuit.demo.vehiclerealtimedatareceiver.dao.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@ToString
@Table("vehicleevents")
public class VehicleEventsEntity {

    @Id
    @PrimaryKey
    private String id;

    @Column("registration_number")
    private String registrationNumber;

    @Column("uuid")
    private String uuid;

    @Column("status")
    private String status;

    @Column("latitude")
    private Double latitude;

    @Column("longitude")
    private Double longitude;

    @Column("distance")
    private Double distance;

    @Column("speed")
    private Double speed;

    @Column("fuel")
    private Double fuel;

    @Column("event_ts")
    private String eventTs;

    @Column("update_ts")
    private LocalDateTime updateTs;
}
