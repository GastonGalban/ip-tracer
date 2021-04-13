package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.client.currconv.CurrConvClient;
import com.gastongalban.iptracer.client.ip2country.IP2CountryClient;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.CurrencyData;
import com.gastongalban.iptracer.model.TraceData;
import com.gastongalban.iptracer.repository.TraceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class TracerService {

    private IP2CountryClient ip2CountryClient;
    private CurrConvClient currConvClient;
    private DistanceCalculator distanceCalculator;
    private CountryService countryService;
    private TraceDataRepository traceDataRepository;

    @Autowired
    public TracerService(IP2CountryClient ip2CountryClient,
                         CurrConvClient currConvClient,
                         DistanceCalculator distanceCalculator,
                         CountryService countryService, TraceDataRepository traceDataRepository) {
        this.ip2CountryClient = ip2CountryClient;
        this.countryService = countryService;
        this.currConvClient = currConvClient;
        this.distanceCalculator = distanceCalculator;
        this.traceDataRepository = traceDataRepository;
    }

    public Optional<TraceData> trace(String ip){

        Optional<String> countryCode = ip2CountryClient.getCountryCode(ip);

        if(countryCode.isPresent()){

            Optional<CountryData> countryData = countryService.getCountry(countryCode.get());

            if(countryData.isPresent()){
                CountryData country = countryData.get();
                TraceData.Builder builder = new TraceData.Builder();
                builder.withIp(ip)
                .withCountry(country.getName())
                .withISOCode(countryCode.get())
                .withDistanceToBsAs(this.distanceCalculator.calculate(country.getLocation()))
                .withDate(new Date());

                country.getLanguages().forEach(builder::withLanguage);
                country.getTimezones().stream().map(this::transformTimeZoneToDate).forEach(builder::withDateData);
                country.getCurrencyCodes().forEach(currCode -> {
                    Optional<Double> price = this.getCurrency(currCode);
                    price.ifPresent(aDouble -> builder.withCurrencyData(new CurrencyData(currCode, aDouble)));
                });
                TraceData traceData = builder.build();
                this.traceDataRepository.insert(traceData);
                return Optional.of(traceData);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    private Optional<Double> getCurrency(String currCode) {
        if(currCode.equals("USD")){
            return Optional.of(1d);
        }
        return currConvClient.getCurrency(currCode);
    }

    private String transformTimeZoneToDate(String timezone) {
        ZoneId of = ZoneId.of(timezone);
        ZonedDateTime now = LocalDateTime.now().atZone(of);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        return now.format(formatter);
    }
}
