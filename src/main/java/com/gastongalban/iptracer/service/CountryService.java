package com.gastongalban.iptracer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gastongalban.iptracer.client.restcountries.RestCountriesClient;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountryService {

    private CountryRepository countryRepository;
    private RestCountriesClient restCountriesClient;

    @Autowired
    public CountryService(CountryRepository countryRepository, RestCountriesClient restCountriesClient) {
        this.countryRepository = countryRepository;
        this.restCountriesClient = restCountriesClient;
    }

    public Optional<CountryData> getCountry(String countryCode){
        Optional<CountryData> countryData = countryRepository.search(countryCode);
        if(countryData.isPresent()){
            return countryData;
        } else {
            countryData = restCountriesClient.getCountryData(countryCode);
            if(countryData.isPresent()){
                try {
                    countryRepository.insert(countryData.get());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
                return countryData;
            }
        }
        return Optional.empty();
    }
}
