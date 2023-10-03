package com.intuit.demo.vehiclerealtimedatareceiver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {

    private String uuid;
    private String registrationNumber;
    private String status;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double distance;
    private Double fuel;
    private String timestamp;
    private LastKnowGeoLocation lastKnowGeoLocation;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LastKnowGeoLocation {
        private Double latitude;
        private Double longitude;
    }
}
