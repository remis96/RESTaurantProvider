package com.michal.RESTaurant.utils;


import com.michal.RESTaurant.entity.restaurant.Restaurant;

import java.io.IOException;
import java.util.List;


public interface IDistanceEvaluator {
    List<Restaurant> getRestaurantsInDistance(Double sourceLong, Double sourceLat, Double distanceInMeters, List<Restaurant> restaurants) throws InterruptedException, IOException;

}
