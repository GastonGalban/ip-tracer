package com.gastongalban.iptracer.client.restcountries;

import com.gastongalban.iptracer.client.ip2country.IP2CountryClient;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RestCountriesClientTest {

    private static final String JSON_RESPONSE = "{\"countryCode\": \"DE\",\"countryCode3\": \"DEU\",\"countryName\": \"Germany\"}";
    @InjectMocks
    private RestCountriesClient client;
    @Mock
    private RestCountriesTransformer transformer;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;
    @Mock
    private CountryData countryData;
    private InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());

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
        when(transformer.transform(any(RestCountriesDTO.class))).thenReturn(countryData);

        Optional<CountryData> code = client.getCountryData("DEU");

        assertTrue(code.isPresent());
        assertEquals(countryData,code.get());
    }

    @Test
    void getCountryDataTest_Fail() throws IOException {
        when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());

        Optional<CountryData> code = client.getCountryData("DEU");

        assertFalse(code.isPresent());
    }

}