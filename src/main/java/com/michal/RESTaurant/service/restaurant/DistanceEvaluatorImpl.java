package com.michal.RESTaurant.service.restaurant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.RESTaurant.config.PropertyVariablesConfig;
import com.michal.RESTaurant.entity.GeoCoordinates;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceEvaluatorImpl implements DistanceEvaluator {
    //najskor vyfiltrovanie objektov v urcitom radiuse aby som zminimalizoval pocet api callov
    @Autowired
    PropertyVariablesConfig propertyVariablesConfig;

    @Override
    public Double getDistanceInMetersBetweenPointsRadius(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB) {
        if ((lattitudeA == lattitudeB) && (longitudeA == longitudeB)) {
            return 0.0;
        } else {
            double theta = longitudeA - longitudeB;
            double dist = Math.sin(Math.toRadians(lattitudeA)) * Math.sin(Math.toRadians(lattitudeB)) + Math.cos(Math.toRadians(lattitudeA)) * Math.cos(Math.toRadians(lattitudeB)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;

            dist = dist * 1.609344;
            System.out.println("Distance by fly path is " + dist);

            return dist / 1000;
            //todo toto dat do pice a urobit to cez sql query
        }
    }

    //volam api aby mi vratila vzdialenost pre spocsob dopravy (wehicle) pre realnu vzdialenost po ceste
    @Override
    public Double getDistanceInMetersBetweenPointsPath(double lattitudeA, double longitudeA, double lattitudeB, double longitudeB, String wehicle) throws IOException {
        String point = "point=";
        String vehicle = "&vehicle=" + wehicle;
        String apiKey = "&key=";
        String request = this.propertyVariablesConfig.getGraphhopper_url() + point +
                lattitudeA + "," + longitudeA + "&" + point + lattitudeB + "," +
                longitudeB + vehicle + "&calc_points" + "&" + this.propertyVariablesConfig.getGrapHopperApiKey();

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

    //parsujem si return z api callu pre realny distance
    @Override
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

    @Override
    public List<Restaurant> getRestaurantsInDistance(GeoCoordinates sourceLocation, Double distanceInMeters, List<Restaurant> restaurants) throws IOException {
        List<Restaurant> restaurantsInRadius = new ArrayList<Restaurant>();
        List<Restaurant> restaurantsInRealDistance = new ArrayList<Restaurant>();
        //first I need to filter all restaurants in ceratin radius and from these I will then find ones with certain distance and add then to restaurantsInRadius
        for (Restaurant restaurant : restaurants) {

            if (getDistanceInMetersBetweenPointsRadius(sourceLocation.getLatitude(), sourceLocation.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude()) <= distanceInMeters) {
                restaurantsInRadius.add(restaurant);
            }
        }
        //then I'll filter them again through google distance matrix api and those that will pass requirements will be added to restaurantsInRealDistance
        for (Restaurant restaurant : restaurantsInRadius) {
            if (getDistanceInMetersBetweenPointsPath(sourceLocation.getLatitude(), sourceLocation.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude(), "car") <= distanceInMeters) {
                restaurantsInRealDistance.add(restaurant);

            }
        }
        return restaurantsInRealDistance;
    }
}
