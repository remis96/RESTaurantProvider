package com.michal.RESTaurant.service.restaurant;

import com.michal.RESTaurant.entity.GeoCoordinates;
import com.michal.RESTaurant.entity.restaurant.Restaurant;

import java.io.IOException;
import java.util.List;


public interface IDistanceEvaluator {
    Double getDistanceInMetersBetweenPointsRadius(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB);

    Double getDistanceInMetersBetweenPointsPath(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB, String wehicle) throws IOException;

    Double parseResponse(String response);

    List<Restaurant> getRestaurantsInDistance(GeoCoordinates sourceLocation, Double distanceInMeters, List<Restaurant> restaurants) throws IOException;
}
