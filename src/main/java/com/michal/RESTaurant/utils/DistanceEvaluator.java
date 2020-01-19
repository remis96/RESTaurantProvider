package com.michal.RESTaurant.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to calculate path between two geo points
 * lets say you have object A and want all objects that are X Km away from him
 * first of all, you will find all objects in X km radius with distanceRadius method
 * then, you will filter those bojects with distance path method
 * this is faster than only using api
 */
public class DistanceEvaluator implements IDistanceEvaluator {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point
     *
     * @returns Distance in Meters
     */
    @Autowired
    RestaurantRepository repository;
    private String GraphhopperURL = "https://graphhopper.com/api/1/route?";

    public static double distanceRadius(double lat1, double lat2, double lon1, double lon2) {
        //https://www.geodatasource.com/developers/java
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;
            System.out.println("Distance by fly path is " + dist);

            return dist / 1000;
        }
    }

    @Override
    public List<Restaurant> getRestaurantsInDistance(Double sourceLong, Double sourceLat, Double distanceInMeters, List<Restaurant> restaurants) throws InterruptedException, IOException {
        List<Restaurant> restaurantsInRadius = new ArrayList<>();
        List<Restaurant> restaurantsInRealDistance = new ArrayList<>();
        List<Restaurant> restaurantsAll = restaurants;
        //first I need to filter all restaurants in ceratin radius and from these I will then find ones with certain distance and add then to restaurantsInRadius
        for (Restaurant restaurant : restaurantsAll) {

            if (distanceRadius(sourceLat, restaurant.getLatitude(), sourceLong, restaurant.getLongitude()) <= distanceInMeters) {

                restaurantsInRadius.add(restaurant);
            }
        }
        //then I'll filter them again through google distance matrix api and those that will pass requirements will be added to restaurantsInRealDistance
        for (Restaurant restaurant : restaurantsInRadius) {
            if (calculateRealPath(restaurant, sourceLong, sourceLat, "car") <= distanceInMeters) {
                restaurantsInRealDistance.add(restaurant);

            }
        }

        return restaurantsInRealDistance;


    }


    public Double calculateRealPath(Restaurant restaurant, Double sourceLongitude, Double sourceLatitude, String whatWehicle) throws IOException {
        String point = "point=";
        String vehicle = "&vehicle=" + whatWehicle;
        String apiKey = "&key=";
        String request = this.GraphhopperURL + point + sourceLatitude + "," + sourceLongitude + "&" + point + restaurant.getLatitude() + "," + restaurant.getLongitude() + vehicle + apiKey;

        System.out.println(request);
        URL url = new URL(request);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return parseResponse(content.toString());
    }


    public Double parseResponse(String response) {
        ObjectMapper mapper = new ObjectMapper();
        Double distance = 0.0;
        try {
            JsonNode root = mapper.readTree(response);
            distance = root.findValue("distance").asDouble();
            System.out.println("Distance from API is " + distance);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return distance;
    }
}
