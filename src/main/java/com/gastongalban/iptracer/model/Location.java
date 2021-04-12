package com.gastongalban.iptracer.model;

import java.io.Serializable;

public class Location implements Serializable {
    private Double lat;
    private Double lon;

    public Location (){

    }

    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
