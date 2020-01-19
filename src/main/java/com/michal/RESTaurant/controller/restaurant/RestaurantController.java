package com.michal.RESTaurant.controller.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.service.restaurant.IRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController implements IRestaurantController {
    @Autowired
    IRestaurantService restaurantService;

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CustomResponse addRestaurant(@RequestBody Restaurant rest) {
        return restaurantService.addRestaurant(rest);

    }

    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        if (restaurantService.getAllRestaurants().isEmpty()) {
            return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
        }
    }

    @Override
    @RequestMapping(value = "/addMenuItem", method = RequestMethod.POST)
    public CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody MenuItem item) {
        return restaurantService.addMenuItem(restaurantId, item);

    }

    @Override
    @RequestMapping(value = "/addExceptionDate", method = RequestMethod.POST)
    public CustomResponse addMenuItem(@RequestParam Long restaurantId, @RequestBody ExceptionDate date) {
        return restaurantService.addExceptionDate(restaurantId, date);

    }

    @Override
    @RequestMapping(value = "/getRestaurantsInRadius", method = RequestMethod.GET)
    public List<Restaurant> getInRadius(@RequestParam Double sourceLong, @RequestParam Double sourceLat, @RequestParam Double distance) throws InterruptedException, IOException {
        return restaurantService.getRestaurantsInDistance(sourceLong, sourceLat, distance);

    }


}
