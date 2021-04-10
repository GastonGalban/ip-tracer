package com.gastongalban.iptracer.repository;

import com.gastongalban.iptracer.model.CountryData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class CountryRepository {

    private HashMap<String, CountryData> countries = new HashMap<>();

    public void save(CountryData countryData){
        countries.put(countryData.getCountryCode(),countryData);
    }

    public Optional<CountryData> search(String countryCode){
        if(countries.containsKey(countryCode)){
            return Optional.of(countries.get(countryCode));
        }
        return Optional.empty();
    }

}
