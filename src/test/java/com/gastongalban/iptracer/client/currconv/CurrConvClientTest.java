package com.gastongalban.iptracer.client.currconv;

import com.gastongalban.iptracer.client.restcountries.dto.RestCountriesDTO;
import com.gastongalban.iptracer.model.CountryData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CurrConvClientTest {
    private static final String JSON_RESPONSE = "{\"countryCode\": \"DE\",\"countryCode3\": \"DEU\",\"countryName\": \"Germany\"}";
    private static final String SOME_CODE = "EUR";
    @InjectMocks
    private CurrConvClient client;
    @Mock
    private CurrConvTransformer transformer;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;
    private InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
    private Double currency = 2.0;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.client.setClient(httpClient);
    }

    @Test
    void getCountryDataTest() throws IOException {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(inputStream);
        when(transformer.transform(anyMap(),eq(SOME_CODE))).thenReturn(currency);

        Optional<Double> price = client.getCurrency("EUR");

        assertTrue(price.isPresent());
        assertEquals(currency,price.get());
    }

    @Test
    void getCountryDataTest_Fail() throws IOException {
        when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());

        Optional<Double> currency = client.getCurrency("EUR");

        assertFalse(currency.isPresent());
    }

}