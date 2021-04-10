package com.gastongalban.iptracer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraceData {

    private String ip;
    private Date date;
    private String country;
    private String ISOCode;
    private List<String> languages;
    private List<String> datesData;
    private Double distanceToBsAs;
    private List<CurrencyData> currencyData;

    public String getIp() {
        return ip;
    }

    public Date getDate() {
        return date;
    }

    public String getCountry() {
        return country;
    }

    public String getISOCode() {
        return ISOCode;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getDatesData() {
        return datesData;
    }

    public Double getDistanceToBsAs() {
        return distanceToBsAs;
    }

    public List<CurrencyData> getCurrencyData() {
        return currencyData;
    }


    public static class Builder {
        private String ip;
        private Date date;
        private String country;
        private String ISOCode;
        private List<String> languages = new ArrayList<>();
        private List<String> datesData = new ArrayList<>();
        private Double distanceToBsAs;
        private List<CurrencyData> currencyData = new ArrayList<>();

        public Builder withIp(String ip){
            this.ip = ip;
            return this;
        }

        public Builder withDate(Date date){
            this.date = date;
            return this;
        }

        public Builder withCountry(String country){
            this.country = country;
            return this;
        }

        public Builder withISOCode(String isoCode){
            this.ISOCode = isoCode;
            return this;
        }

        public Builder withLanguage(String language){
            this.languages.add(language);
            return this;
        }

        public Builder withDistanceToBsAs(Double distanceToBsAs){
            this.distanceToBsAs = distanceToBsAs;
            return this;
        }

        public Builder withCurrencyData(CurrencyData currencyData){
            this.currencyData.add(currencyData);
            return this;
        }

        public Builder withDateData(String date){
            this.datesData.add(date);
            return this;
        }

        public TraceData build(){
            TraceData traceData = new TraceData();

            traceData.ip = this.ip;
            traceData.date = this.date;
            traceData.country = this.country;
            traceData.ISOCode = this.ISOCode;
            traceData.languages = this.languages;
            traceData.datesData = this.datesData;
            traceData.distanceToBsAs = this.distanceToBsAs;
            traceData.currencyData = this.currencyData;

            return traceData;
        }
    }

}
