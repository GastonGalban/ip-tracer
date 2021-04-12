package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.repository.TraceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReportService {

    private TraceDataRepository traceDataRepository;

    @Autowired
    public ReportService(TraceDataRepository traceDataRepository) {
        this.traceDataRepository = traceDataRepository;
    }

    public Optional<Double> getMaxDistance() {
        Double maxDistance = traceDataRepository.getMaxDistance();
        return Optional.of(maxDistance);
    }

    public Optional<Double> getMinDistance() {
        Double minDistance = traceDataRepository.getMinDistance();
        return Optional.of(minDistance);
    }

    public Optional<Double> getAverageDistance() {
        List<TraceData> traceDataList = traceDataRepository.getAll();

        if(!traceDataList.isEmpty()){
            Map<String, List<TraceData>> groupedTraceData = traceDataList.stream().collect(Collectors.groupingBy(TraceData::getISOCode));

            Double totalKm = groupedTraceData.values().stream()
                    .map(countryData -> countryData.get(0).getDistanceToBsAs() * countryData.size())
                    .reduce(0d, Double::sum);

            return Optional.of(totalKm/traceDataList.size());
        }
        return Optional.of(0d);
    }
}
