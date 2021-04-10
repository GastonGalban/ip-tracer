package com.gastongalban.iptracer.service;

import com.gastongalban.iptracer.client.restcountries.RestCountriesClient;
import com.gastongalban.iptracer.model.CountryData;
import com.gastongalban.iptracer.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private static final String SOME_COUNTRY_CODE = "ARG";
    @InjectMocks
    private CountryService countryService;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private RestCountriesClient restCountriesClient;
    @Mock
    private CountryData countryData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCountryFromRepository() {

        when(countryRepository.search(eq(SOME_COUNTRY_CODE))).thenReturn(Optional.of(countryData));

        Optional<CountryData> actualCountryData = countryService.getCountry(SOME_COUNTRY_CODE);

        assertTrue(actualCountryData.isPresent());
        assertEquals(countryData, actualCountryData.get());

        verifyNoInteractions(restCountriesClient);

    }

    @Test
    void getCountryFromClient() {

        when(countryRepository.search(eq(SOME_COUNTRY_CODE))).thenReturn(Optional.empty());
        when(restCountriesClient.getCountryData(eq(SOME_COUNTRY_CODE))).thenReturn(Optional.of(countryData));

        Optional<CountryData> actualCountryData = countryService.getCountry(SOME_COUNTRY_CODE);

        assertTrue(actualCountryData.isPresent());
        assertEquals(countryData, actualCountryData.get());

        verify(restCountriesClient).getCountryData(SOME_COUNTRY_CODE);
        verify(countryRepository).search(SOME_COUNTRY_CODE);

    }

    @Test
    void getCountryNotFound() {

        when(countryRepository.search(eq(SOME_COUNTRY_CODE))).thenReturn(Optional.empty());
        when(restCountriesClient.getCountryData(eq(SOME_COUNTRY_CODE))).thenReturn(Optional.empty());

        Optional<CountryData> actualCountryData = countryService.getCountry(SOME_COUNTRY_CODE);

        assertFalse(actualCountryData.isPresent());

        verify(restCountriesClient).getCountryData(SOME_COUNTRY_CODE);
        verify(countryRepository).search(SOME_COUNTRY_CODE);

    }
}