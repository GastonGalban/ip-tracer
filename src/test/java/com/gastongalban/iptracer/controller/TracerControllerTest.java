package com.gastongalban.iptracer.controller;

import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.service.TracerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

class TracerControllerTest {

    @InjectMocks
    private TracerController controller;
    @Mock
    private TracerService tracerService;
    @Mock
    private TraceData traceData;
    private static final String SOME_IP = "111.111.111.111";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(tracerService.trace(SOME_IP)).thenReturn(Optional.of(traceData));
    }

    @Test
    void getTracing() {
        TraceData tracing = this.controller.getTracing(SOME_IP);

        assertSame(traceData,tracing);
    }
}