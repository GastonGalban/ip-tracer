package com.gastongalban.iptracer.client.restcountries.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDTO {

    private String iso639_1;
    private String name;

    public String getIso639_1() {
        return iso639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIso639_1(String iso639_1) {
        this.iso639_1 = iso639_1;
    }
}
