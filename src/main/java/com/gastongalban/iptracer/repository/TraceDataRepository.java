package com.gastongalban.iptracer.repository;

import com.gastongalban.iptracer.model.TraceData;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TraceDataRepository {

    private List<TraceData> traceData = new LinkedList<>();

    public void save(TraceData traceData){
        this.traceData.add(traceData);
    }

    public Double getMaxDistance() {
        return null;
    }

    public Double getMinDistance() {
        return null;
    }

    public List<TraceData> getAll() {
        return null;
    }
}
