package com.michal.RESTaurant.utils;

public class GeoCoordinates {
    private Double longitude;
    private Double latitude;

    // <editor-fold defaultstate="collapsed" desc="getters/setters">

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "GeoCoordinates{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
    // </editor-fold>
}
