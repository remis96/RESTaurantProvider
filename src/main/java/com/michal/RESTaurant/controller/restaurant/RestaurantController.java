package com.michal.RESTaurant.controller.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CustomResponse addRestaurant(@RequestBody Restaurant rest) {
        return restaurantService.addRestaurant(rest);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        if (restaurantService.getAllRestaurants().isEmpty()) {
            return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResponseEntity<Restaurant> getRestaurantById(@RequestParam("id") Long id) {
        if (restaurantService.findrestaurantById(id).isPresent()) {
            return new ResponseEntity<>(restaurantService.findrestaurantById(id).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(restaurantService.findrestaurantById(id).get(), HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/menuItem", method = RequestMethod.POST)
    public CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody MenuItem item) {
        return restaurantService.addMenuItem(restaurantId, item);

    }

    @RequestMapping(value = "/exceptionDate", method = RequestMethod.POST)
    public CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody ExceptionDate date) {
        return restaurantService.addExceptionDate(restaurantId, date);

    }

    @RequestMapping(value = "/getInDistance", method = RequestMethod.GET)
    public List<Restaurant> getInRadius(@RequestParam Double sourceLong, @RequestParam Double sourceLat, @RequestParam Double distance) throws InterruptedException, IOException {
        return restaurantService.getRestaurantsInDistance(sourceLong, sourceLat, distance);
    }


}
