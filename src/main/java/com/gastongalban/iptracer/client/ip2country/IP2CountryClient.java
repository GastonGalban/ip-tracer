package com.gastongalban.iptracer.client.ip2country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastongalban.iptracer.client.ip2country.model.IP2CountryDTO;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
public class IP2CountryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(IP2CountryClient.class);
    private static final String URI_FORMAT = "https://api.ip2country.info/ip?%s";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient client = HttpClients.custom().build();

    public Optional<String> getCountryCode(String ip){
        LOGGER.info("Getting country info for IP {}", ip);
            HttpGet request = new HttpGet(this.createURI(ip));
        try {
            HttpResponse response = client.execute(request);
            String bodyResponse = EntityUtils.toString(response.getEntity());
            IP2CountryDTO countryCode = objectMapper.readValue(bodyResponse, IP2CountryDTO.class);
            return Optional.of(countryCode.getCountryCode3());
        } catch (RuntimeException | IOException e) {
            LOGGER.error("There was an error while executing request",e);
            return Optional.empty();
        }
    }

    private String createURI(String ip) {
        return String.format(URI_FORMAT,ip);
    }

    protected void setClient(HttpClient client){
        this.client = client;
    }
}
