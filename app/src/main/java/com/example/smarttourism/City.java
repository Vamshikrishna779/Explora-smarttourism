package com.example.smarttourism;

public class City {
    private String cityName;
    private String cityDescription;
    private double latitude;
    private double longitude;
    private double cityRating;

    public City(String cityName, String cityDescription, double latitude, double longitude) {
        this.cityName = cityName;
        this.cityDescription = cityDescription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getCityRating() {
        return cityRating;
    }
}
