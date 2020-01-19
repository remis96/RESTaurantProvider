package com.michal.RESTaurant.controller.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface IRestaurantController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    CustomResponse addRestaurant(@RequestBody Restaurant rest);

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<List<Restaurant>> getAllRestaurants();

    @RequestMapping(value = "/addMenuItem", method = RequestMethod.POST)
    CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody MenuItem item);

    @RequestMapping(value = "/addExceptionDate", method = RequestMethod.POST)
    CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody ExceptionDate date);

    // @RequestMapping(value = "/getRestaurantsInRadius", method = RequestMethod.GET)
    // List<Restaurant> getInRadius(@RequestParam Double sourceLong, @RequestParam Double sourceLat, @RequestParam Double distance) throws InterruptedException, IOException;
}
