package com.gastongalban.iptracer.client.currconv;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrConvTransformer {

    public Double transform(Map<String,Double> currencies, String currencyCode){
        String priceCode = String.format("%s_USD",currencyCode);
        return currencies.get(priceCode);
    }
}
