package com.gastongalban.iptracer.model;

import java.util.List;

public class CountryData {

    private String countryCode;
    private String name;
    private List<String> languages;
    private Location location;
    private List<String> timezones;
    private List<String> currencyCodes;

    public CountryData(){

    }

    public CountryData(String countryCode, String name, List<String> languages, Location location, List<String> timezones, List<String> currencyCodes) {
        this.countryCode = countryCode;
        this.name = name;
        this.languages = languages;
        this.location = location;
        this.timezones = timezones;
        this.currencyCodes = currencyCodes;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCurrencyCodes(List<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }
}
