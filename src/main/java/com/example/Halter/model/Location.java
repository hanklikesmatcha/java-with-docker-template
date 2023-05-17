package com.example.JavaWithDocker.model;

import java.io.Serializable;

public class Location implements Serializable{
    private String lat;
    private String lng;

    public Location() {}

    public Location (String lat, String lng){
        this.lat = lat;
        this.lng = lng;
    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLong(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
    
}
