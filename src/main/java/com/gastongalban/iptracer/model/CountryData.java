package com.gastongalban.iptracer.model;

import java.util.List;

public class CountryData {

    private String name;
    private List<String> languages;
    private Location location;
    private List<String> timezones;
    private List<String> currencyCodes;

    public CountryData(String name, List<String> languages, Location location, List<String> timezones, List<String> currencyCodes) {
        this.name = name;
        this.languages = languages;
        this.location = location;
        this.timezones = timezones;
        this.currencyCodes = currencyCodes;
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
}
