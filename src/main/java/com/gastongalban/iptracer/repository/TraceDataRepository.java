package com.gastongalban.iptracer.repository;

import com.gastongalban.iptracer.model.TraceData;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class TraceDataRepository {

    private List<TraceData> traceData = new LinkedList<>();

    public void save(TraceData traceData){
        this.traceData.add(traceData);
    }

    public Optional<Double> getMaxDistance() {
        return traceData.stream().map(TraceData::getDistanceToBsAs).max(Double::compareTo);
    }

    public Optional<Double> getMinDistance() {
        return traceData.stream().map(TraceData::getDistanceToBsAs).min(Double::compareTo);
    }

    public List<TraceData> getAll() {
        return traceData;
    }
}
