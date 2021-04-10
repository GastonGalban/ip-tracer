package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.client.currconv.CurrConvClient;
import com.gastongalban.iptracer.client.ip2country.IP2CountryClient;
import com.gastongalban.iptracer.client.restcountries.RestCountriesClient;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.CurrencyData;
import com.gastongalban.iptracer.model.TraceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class TracerService {

    private IP2CountryClient ip2CountryClient;
    private RestCountriesClient restCountriesClient;
    private CurrConvClient currConvClient;
    private DistanceCalculator distanceCalculator;

    @Autowired
    public TracerService(IP2CountryClient ip2CountryClient,
                         RestCountriesClient restCountriesClient,
                         CurrConvClient currConvClient,
                         DistanceCalculator distanceCalculator) {
        this.ip2CountryClient = ip2CountryClient;
        this.restCountriesClient = restCountriesClient;
        this.currConvClient = currConvClient;
        this.distanceCalculator = distanceCalculator;
    }

    public Optional<TraceData> trace(String ip){

        Optional<String> countryCode = ip2CountryClient.getCountryCode(ip);
        if(countryCode.isPresent()){
            Optional<CountryData> countryData = restCountriesClient.getCountryData(countryCode.get());

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
                    Optional<Double> price = currConvClient.getCurrency(currCode);
                    price.ifPresent(aDouble -> builder.withCurrencyData(new CurrencyData(currCode, aDouble)));
                });
                return Optional.of(builder.build());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    private String transformTimeZoneToDate(String timezone) {
        ZoneId of = ZoneId.of(timezone);
        ZonedDateTime now = LocalDateTime.now().atZone(of);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        return now.format(formatter);
    }
}
