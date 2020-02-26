package com.michal.RESTaurant.service.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.restaurant.Restaurant;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RestaurantService {


    /**
     * @param id id of the restaurant
     * @return optional restaurant
     */
    Optional<Restaurant> findrestaurantById(Long id);

    List<Restaurant> getAllRestaurants();

    /**
     * @param restaurantId id of the restaurant
     * @return set of menu items for restaurant
     */
    Set<MenuItem> getMenuForRestaurant(Long restaurantId);

    /**
     * @param restaurant
     * @return custom response with message and http status
     */
    CustomResponse addRestaurant(Restaurant restaurant);

    /**
     * @param restaurantId id of restaurant
     * @param menuItem     menu item to be added to restaurant if found
     * @return custom response with message and http status
     */
    CustomResponse addMenuItem(Long restaurantId, MenuItem menuItem);

    void initializeOpeningHours();

    CustomResponse addExceptionDate(Long restaurantId, ExceptionDate date);

    void initializeOpeningHoursRestaurant(Restaurant res);

    CustomResponse setHidden(Long restaurantId);

    List<Restaurant> getRestaurantsInDistance(Double sourceLong, Double sourceLat, Double distanceInMeters) throws InterruptedException, IOException;

    Optional<List<Restaurant>> getRestaurantsByRating(Float raing);

    Optional<List<Restaurant>> getRestaurantsByPriceClass(Float priceClass);

    Optional<List<Restaurant>> getRestaurantsByPriceClassAndRating(Float rating, Float priceClass);

    Optional<List<Restaurant>> getRestaurantsByDistancePriceClassAndRating(Double sourceLong, Double sourceLat, Double distanceInMeters,
                                                                           Float rating, Float priceClass) throws InterruptedException, IOException;

    Optional<List<Restaurant>> getByDistanceAndRating(Double sourceLong, Double sourceLat, Double distanceInMeters,
                                                      Float rating) throws InterruptedException, IOException;

    Optional<List<Restaurant>> getByDistanceAndPriceClass(Double sourceLong, Double sourceLat, Double distanceInMeters,
                                                          Float priceClass) throws InterruptedException, IOException;

}
