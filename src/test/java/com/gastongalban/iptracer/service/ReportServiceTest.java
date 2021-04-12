package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.repository.TraceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private TraceDataRepository traceDataRepository;
    @Mock
    private TraceData firstTraceData;
    @Mock
    private TraceData secondTraceData;
    @Mock
    private TraceData thirdTraceData;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMaxDistance() {
        when(traceDataRepository.getMaxDistance()).thenReturn(100d);

        Optional<Double> maxDistance = reportService.getMaxDistance();

        assertTrue(maxDistance.isPresent());
        assertEquals(100d, maxDistance.get());
    }

    @Test
    void getMinDistance() {
        when(traceDataRepository.getMinDistance()).thenReturn(1d);

        Optional<Double> minDistance = reportService.getMinDistance();

        assertTrue(minDistance.isPresent());
        assertEquals(1d, minDistance.get());
    }

    @Test
    void getAverageDistance() {
        when(firstTraceData.getISOCode()).thenReturn("USA");
        when(secondTraceData.getISOCode()).thenReturn("USA");
        when(thirdTraceData.getISOCode()).thenReturn("ESP");
        when(firstTraceData.getDistanceToBsAs()).thenReturn(100d);
        when(secondTraceData.getDistanceToBsAs()).thenReturn(100d);
        when(thirdTraceData.getDistanceToBsAs()).thenReturn(50d);
        List<TraceData> traceDataList = Arrays.asList(firstTraceData, secondTraceData, thirdTraceData);

        when(traceDataRepository.getAll()).thenReturn(traceDataList);

        Optional<Double> averageDistance = reportService.getAverageDistance();

        assertTrue(averageDistance.isPresent());
        assertEquals(83.33d, averageDistance.get(),2);
    }

    @Test
    void getAverageDistance_emptyList() {

        List<TraceData> traceDataList = Collections.emptyList();

        when(traceDataRepository.getAll()).thenReturn(traceDataList);

        Optional<Double> averageDistance = reportService.getAverageDistance();

        assertTrue(averageDistance.isPresent());
        assertEquals(0d, averageDistance.get(),2);
    }
}