package com.gastongalban.iptracer.controller;

import com.gastongalban.iptracer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report/max-distance")
    public Double getMaxDistanceReport(){

        Optional<Double> trace = reportService.getMaxDistance();
        return trace.orElse(null);
    }

    @GetMapping("/report/min-distance")
    public Double getMinDistanceReport(){

        Optional<Double> trace = reportService.getMinDistance();
        return trace.orElse(null);
    }

    @GetMapping("/report/average-distance")
    public Double getAverageDistanceReport(){

        Optional<Double> trace = reportService.getAverageDistance();
        return trace.orElse(null);
    }
}
