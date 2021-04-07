package com.gastongalban.iptracer.client.currconv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrConvTransformerTest {

    private CurrConvTransformer transformer = new CurrConvTransformer();
    private HashMap<String,Double> currencies = new HashMap<>();

    @BeforeEach
    void setUp() {
        currencies.put("EUR_USD",2.9d);
        currencies.put("ARS_USD",3.9d);
        currencies.put("COL_USD",1.9d);
        currencies.put("MXN_USD",5.9d);
    }

    @Test
    void transformTest() {
        Double eur = this.transformer.transform(currencies, "EUR");

        assertEquals(2.9d,eur,0);
    }

}