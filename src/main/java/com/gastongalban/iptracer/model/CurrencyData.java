package com.gastongalban.iptracer.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyData that = (CurrencyData) o;
        return Objects.equals(code, that.code) && Objects.equals(usdPrice, that.usdPrice);
    }
}
