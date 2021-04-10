package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.Location;
import org.springframework.stereotype.Component;

@Component
public class DistanceCalculator {

    private static final Double bsAsLat = -34d;
    private static final Double bsAsLon = -64d;
    private static final Double earthRadius = 6378d;

    public Double calculate(Location location) {
        return Double.NaN;
    }

}
