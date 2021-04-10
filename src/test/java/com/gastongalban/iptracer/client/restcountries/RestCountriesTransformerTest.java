package com.gastongalban.iptracer.client.restcountries;

import com.gastongalban.iptracer.client.restcountries.dto.CurrencyDTO;
import com.gastongalban.iptracer.client.restcountries.dto.LanguageDTO;
import com.gastongalban.iptracer.client.restcountries.dto.RestCountriesDTO;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.when;

class RestCountriesTransformerTest {

    RestCountriesTransformer transformer = new RestCountriesTransformer();
    @Mock
    private RestCountriesDTO restCountriesDTO;
    private Map<String, String> translations = new HashMap<>();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        translations.put("es","Alemania");

        when(restCountriesDTO.getName()).thenReturn("Germany");
        when(restCountriesDTO.getTranslations()).thenReturn(translations);
        when(restCountriesDTO.getLatlng()).thenReturn(Arrays.asList(4.0,-72.0));
        CurrencyDTO eur = new CurrencyDTO();
        eur.setCode("EUR");
        when(restCountriesDTO.getCurrencies()).thenReturn(Collections.singletonList(eur));
        when(restCountriesDTO.getTimezones()).thenReturn(Collections.singletonList("UTC+01:00"));
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setName("German");
        languageDTO.setIso639_1("de");
        when(restCountriesDTO.getLanguages()).thenReturn(Collections.singletonList(languageDTO));

    }
    @Test
    void transformTest() {
        String countryCode = "DEU";
        CountryData transform = transformer.transform(restCountriesDTO, countryCode);
        assertEquals("Alemania (Germany)", transform.getName());
        Location location = transform.getLocation();
        assertEquals(4.0, location.getLat(),0);
        assertEquals(-72.0, location.getLon(),0);
        assertEquals("EUR",transform.getCurrencyCodes().get(0));
        assertEquals("German (de)",transform.getLanguages().get(0));
        assertEquals("UTC+01:00",transform.getTimezones().get(0));
    }
}