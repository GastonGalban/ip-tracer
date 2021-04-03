package com.gastongalban.iptracer.model;

import java.util.Date;
import java.util.List;

public class TraceData {

    private String ip;
    private Date date;
    private String country;
    private String ISOCode;
    private List<String> languages;
    private List<Date> datesData;
    private Double distanceToBsAs;
    private CurrencyData currencyData;

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

    public List<Date> getDatesData() {
        return datesData;
    }

    public Double getDistanceToBsAs() {
        return distanceToBsAs;
    }

    public CurrencyData getCurrencyData() {
        return currencyData;
    }


    public static class Builder {
        private String ip;
        private Date date;
        private String country;
        private String ISOCode;
        private List<String> languages;
        private List<Date> datesData;
        private Double distanceToBsAs;
        private CurrencyData currencyData;

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
