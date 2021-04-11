package com.gastongalban.iptracer.controller;

import com.gastongalban.iptracer.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;
    @Mock
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMaxDistanceReport() {
        when(reportService.getMaxDistance()).thenReturn(Optional.of(100D));

        Double maxDistanceReport = reportController.getMaxDistanceReport();

        assertEquals(100D, maxDistanceReport);
    }

    @Test
    void getMinDistanceReport() {
        when(reportService.getMaxDistance()).thenReturn(Optional.of(1D));

        Double maxDistanceReport = reportController.getMaxDistanceReport();

        assertEquals(1D, maxDistanceReport);
    }

    @Test
    void getAverageDistanceReport() {
        when(reportService.getMaxDistance()).thenReturn(Optional.of(50D));

        Double maxDistanceReport = reportController.getMaxDistanceReport();

        assertEquals(50D,maxDistanceReport);
    }
}