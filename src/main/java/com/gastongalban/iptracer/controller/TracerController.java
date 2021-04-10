package com.gastongalban.iptracer.controller;

import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.service.TracerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TracerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TracerController.class);
    private TracerService tracerService;

    @Autowired
    public TracerController(TracerService tracerService) {
        this.tracerService = tracerService;
    }

    @GetMapping("/trace")
    public TraceData getTracing(@RequestParam String ip){
        LOGGER.info("Tracing IP {}",ip);

        Optional<TraceData> trace = tracerService.trace(ip);
        return trace.orElse(null);
    }
}
