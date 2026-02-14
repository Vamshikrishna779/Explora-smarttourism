package com.example.smarttourism;
public class PlaceModel {
    private String city;
    private double latitude;
    private double longitude;
    private double ratings;
    private String idealDuration;
    private String bestTimeToVisit;
    private String cityDesc;

    public PlaceModel(String city, double latitude, double longitude, double ratings, String idealDuration, String bestTimeToVisit, String cityDesc) {
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ratings = ratings;
        this.idealDuration = idealDuration;
        this.bestTimeToVisit = bestTimeToVisit;
        this.cityDesc = cityDesc;
    }

    public String getCity() { return city; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double getRatings() { return ratings; }
    public String getIdealDuration() { return idealDuration; }
    public String getBestTimeToVisit() { return bestTimeToVisit; }
    public String getCityDesc() { return cityDesc; }
}
