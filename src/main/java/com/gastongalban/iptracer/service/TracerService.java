package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.TraceData;
import org.springframework.stereotype.Service;

@Service
public class TracerService {

    public TraceData trace(String ip){
        TraceData.Builder builder = new TraceData.Builder();
        return builder.build();
    }
}
