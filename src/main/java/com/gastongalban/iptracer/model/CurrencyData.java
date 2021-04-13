package com.gastongalban.iptracer.model;

import java.io.Serializable;
import java.util.Objects;

public class CurrencyData implements Serializable {

    private String code;
    private Double usdPrice;

    public CurrencyData(){

    }
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setUsdPrice(Double usdPrice) {
        this.usdPrice = usdPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyData that = (CurrencyData) o;
        return Objects.equals(code, that.code) && Objects.equals(usdPrice, that.usdPrice);
    }
}
