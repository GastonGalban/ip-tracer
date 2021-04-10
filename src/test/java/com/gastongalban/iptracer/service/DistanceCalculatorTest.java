package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    DistanceCalculator distanceCalculator = new DistanceCalculator();

    @Test
    void calculate() {
        Double calculated = distanceCalculator.calculate(new Location(40d,-4d));
        assertEquals(10274,calculated,1);
    }
}