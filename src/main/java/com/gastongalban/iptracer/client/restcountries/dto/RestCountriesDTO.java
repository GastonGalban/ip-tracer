package com.gastongalban.iptracer.client.restcountries.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCountriesDTO {

    private String name;
    private List<Double> latlng;
    private List<String> timezones;
    private List<CurrencyDTO> currencies;
    private List<LanguageDTO> languages;
    private Map<String,String> translations;

    public String getName() {
        return name;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<CurrencyDTO> getCurrencies() {
        return currencies;
    }

    public List<LanguageDTO> getLanguages() {
        return languages;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }
}
