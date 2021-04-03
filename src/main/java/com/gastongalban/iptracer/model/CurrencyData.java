package com.gastongalban.iptracer.model;

public class CurrencyData {

    private String code;
    private Double usdPrice;

    public CurrencyData(String code, Double usdPrice) {
        this.code = code;
        this.usdPrice = usdPrice;
    }

    public String getCode() {
        return code;
    }

    public Double getUsdPrice() {
        return usdPrice;
    }
}
