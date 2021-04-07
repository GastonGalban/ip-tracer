package com.gastongalban.iptracer.client.currconv;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class CurrConvClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrConvClient.class);
    private static final String URI_FORMAT = "https://free.currconv.com/api/v7/convert?q=%s_USD&compact=ultra&apiKey=5eb2db61d8fd67a8077e";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient client = HttpClients.custom().build();

    private  CurrConvTransformer transformer;

    @Autowired
    public CurrConvClient(CurrConvTransformer transformer) {
        this.transformer = transformer;
    }

    public Optional<Double> getCurrency(String currencyCode){
        LOGGER.info("Getting USD currency for {}", currencyCode);
        HttpGet request = new HttpGet(this.createURI(currencyCode));
        try {
            HttpResponse response = client.execute(request);
            String bodyResponse = EntityUtils.toString(response.getEntity());
            Map<String,Double> actualCurrency = objectMapper.readValue(bodyResponse, Map.class);
            return Optional.of(transformer.transform(actualCurrency,currencyCode));
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
