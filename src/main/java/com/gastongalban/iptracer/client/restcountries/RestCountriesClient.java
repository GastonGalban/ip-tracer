package com.gastongalban.iptracer.client.restcountries;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastongalban.iptracer.client.restcountries.dto.RestCountriesDTO;
import com.gastongalban.iptracer.model.CountryData;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class RestCountriesClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestCountriesClient.class);
    private static final String URI_FORMAT = "https://restcountries.eu/rest/v2/alpha/%s";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient client = HttpClients.custom().build();
    private RestCountriesTransformer transformer;

    public RestCountriesClient(RestCountriesTransformer transformer) {
        this.transformer = transformer;
    }

    public Optional<CountryData> getCountryData(String countryCode){
        LOGGER.info("Getting country info for Country code {}", countryCode);
        HttpGet request = new HttpGet(this.createURI(countryCode));
        try {
            HttpResponse response = client.execute(request);
            String bodyResponse = EntityUtils.toString(response.getEntity());
            RestCountriesDTO restCountriesDTO = objectMapper.readValue(bodyResponse, RestCountriesDTO.class);
            return Optional.of(this.transformer.transform(restCountriesDTO));
        } catch (RuntimeException | IOException e) {
            LOGGER.error("There was an error while executing request",e);
            return Optional.empty();
        }
    }

    private String createURI(String countryCode) {
        return String.format(URI_FORMAT,countryCode);
    }

    protected void setClient(HttpClient client){
        this.client = client;
    }
}
