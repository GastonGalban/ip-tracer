package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.Location;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {

    private static final Double bsAsLat = -34d;
    private static final Double bsAsLon = -64d;
    private static final Double earthRadius = 6371d;

    public Double calculate(Location location) {
        double dLat = Math.toRadians(bsAsLat - location.getLat());
        double dLng = Math.toRadians(bsAsLon - location.getLon());
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(location.getLat())) * Math.cos(Math.toRadians(bsAsLat));
        Double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        return earthRadius * va2;
    }

}
