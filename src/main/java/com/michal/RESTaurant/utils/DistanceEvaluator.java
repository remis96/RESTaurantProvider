package com.michal.RESTaurant.utils;

import com.michal.RESTaurant.entity.restaurant.Restaurant;

import java.io.IOException;
import java.util.List;

public interface DistanceEvaluator {
    Double getDistanceInMetersBetweenPointsRadius(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB);

    //volam api aby mi vratila vzdialenost pre spocsob dopravy (wehicle) pre realnu vzdialenost po ceste
    Double getDistanceInMetersBetweenPointsPath(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB, String wehicle) throws IOException;

    //parsujem si return z api callu pre realny distance
    Double parseResponse(String response);

    List<Restaurant> getRestaurantsInDistance(GeoCoordinates sourceLocation, Double distanceInMeters, List<Restaurant> restaurants) throws IOException;
}
