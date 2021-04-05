package com.gastongalban.iptracer.client.ip2country;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IP2CountryClientTest {

    private static final String JSON_RESPONSE = "{\"countryCode\": \"DE\",\"countryCode3\": \"DEU\",\"countryName\": \"Germany\"}";
    private IP2CountryClient client = new IP2CountryClient();
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse httpResponse;
    @Mock
    private HttpEntity httpEntity;
    private InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.client.setClient(httpClient);
    }

    @Test
    void getCountryCodeTest() throws IOException {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(inputStream);

        Optional<String> code = client.getCountryCode("111.111.111.111");

        assertTrue(code.isPresent());
        assertEquals("DEU",code.get());
    }

    @Test
    void getCountryCodeTest_Fail() throws IOException {
        when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());

        Optional<String> code = client.getCountryCode("111.111.111.111");

        assertFalse(code.isPresent());
    }
}