package com.gastongalban.iptracer.client.restcountries;

import com.gastongalban.iptracer.client.restcountries.dto.CurrencyDTO;
import com.gastongalban.iptracer.client.restcountries.dto.RestCountriesDTO;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RestCountriesTransformer {

    public CountryData transform(RestCountriesDTO restCountriesDTO, String countryCode) {
        String name = this.transformName(restCountriesDTO.getName(),restCountriesDTO.getTranslations());

        List<String> languages = restCountriesDTO.getLanguages()
                .stream().map(languageDTO -> String.format("%s (%s)",languageDTO.getName(),languageDTO.getIso639_1()))
                .collect(Collectors.toList());

        Location location = new Location(restCountriesDTO.getLatlng().get(0),restCountriesDTO.getLatlng().get(1));

        List<String> timezones = restCountriesDTO.getTimezones();

        List<String> currencyCodes = restCountriesDTO.getCurrencies().stream().map(CurrencyDTO::getCode).collect(Collectors.toList());

        return new CountryData(countryCode, name, languages, location, timezones, currencyCodes);
    }

    private String transformName(String name, Map<String, String> translations) {
        if(translations.containsKey("es")){
            return String.format("%s (%s)",translations.get("es"),name);
        }
        return name;
    }
}
