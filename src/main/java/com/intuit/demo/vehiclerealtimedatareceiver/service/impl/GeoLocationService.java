package com.intuit.demo.vehiclerealtimedatareceiver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

@Slf4j
@Service
public class GeoLocationService implements LocationService<Double, Double> {

    @Override
    public double calculateDistance(Tuple2<Double, Double> currentGeoLocation, Tuple2<Double, Double> lastKnownGeoLocation, double averageSpeed) {
        double lastKnownLatitude =  lastKnownGeoLocation.getT1();
        double lastKnownLongitude =  lastKnownGeoLocation.getT2();

        double latitude = currentGeoLocation.getT1();
        double longitude = currentGeoLocation.getT2();

        double distance = haversine(lastKnownLatitude, lastKnownLongitude, latitude, longitude);

        return distance / averageSpeed;
    }

    private static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
