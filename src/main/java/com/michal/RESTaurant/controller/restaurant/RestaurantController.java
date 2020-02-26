package com.michal.RESTaurant.controller.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.enums.TypeOfUser;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.service.restaurant.RestaurantService;
import com.michal.RESTaurant.service.user.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, JwtUserDetailsService jwtUserDetailsService) {
        this.restaurantService = restaurantService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CustomResponse addRestaurant(@RequestBody Restaurant rest, Principal principal) {
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

    @RequestMapping(value = "/hide", method = RequestMethod.POST)
    public CustomResponse hideRestaurant(@RequestParam("id") Long id, Principal principal) {
        if (jwtUserDetailsService.getTypeOfUser(principal.getName()).isPresent()) {
            if (jwtUserDetailsService.getTypeOfUser(principal.getName()).get() == TypeOfUser.ADMIN) {
                return restaurantService.setHidden(id);
            }
        }
        return new CustomResponse(HttpStatus.UNAUTHORIZED, "No privileges");
    }

    @RequestMapping(value = "/getByRating", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getRestaurantByRating(@RequestParam("ratingMin") Float rating) {

        if (restaurantService.getRestaurantsByRating(rating).isPresent()) {
            return new ResponseEntity<>(restaurantService.getRestaurantsByRating(rating).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(restaurantService.getRestaurantsByRating(rating).get(), HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/getByCriteria", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> geByCriteria(@RequestParam(value = "ratingMin", required = false) Float rating, @RequestParam(value = "priceClassMin", required = false) Float priceClass,
                                                         @RequestParam(value = "distance", required = false) Double distance, @RequestParam(value = "sourceLong", required = false) Double longitude,
                                                         @RequestParam(value = "sourceLat", required = false) Double latitude) throws IOException, InterruptedException {
        Optional<List<Restaurant>> found = Optional.empty();
        //if only rating and price class are present (if distance is null i dont care about long and lat
        if (rating != null && priceClass != null && distance == null) {
            if (restaurantService.getRestaurantsByPriceClassAndRating(rating, priceClass).isEmpty()) {
                return new ResponseEntity<>(restaurantService.getRestaurantsByPriceClassAndRating(rating, priceClass).get(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(restaurantService.getRestaurantsByPriceClassAndRating(rating, priceClass).get(), HttpStatus.OK);
            }
        }
        //if only price class is present
        if (priceClass != null && rating == null && distance == null) {
            if (restaurantService.getRestaurantsByPriceClass(priceClass).isPresent()) {
                return new ResponseEntity<>(restaurantService.getRestaurantsByPriceClass(priceClass).get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(restaurantService.getRestaurantsByPriceClass(priceClass).get(), HttpStatus.NO_CONTENT);
            }
        }
        //only rating is present
        if (priceClass == null && rating != null && distance == null) {
            if (restaurantService.getRestaurantsByRating(rating).isPresent()) {
                return new ResponseEntity<>(restaurantService.getRestaurantsByRating(rating).get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(restaurantService.getRestaurantsByRating(rating).get(), HttpStatus.NO_CONTENT);
            }
        }
        //only distance, lat, long and rating class are present
        if (rating != null && priceClass == null && distance != null && latitude != null && longitude != null) {
            found = restaurantService.getByDistanceAndRating(longitude, latitude, distance, rating);
            if (found.isEmpty()) {
                return new ResponseEntity<>(found.get(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(found.get(), HttpStatus.OK);
            }
        }
        //only distane, lat, long and price class are present
        if (rating == null && priceClass != null && distance != null && latitude != null && longitude != null) {
            found = restaurantService.getByDistanceAndPriceClass(longitude, latitude, distance, priceClass);
            if (found.isEmpty()) {
                return new ResponseEntity<>(found.get(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(found.get(), HttpStatus.OK);
            }
        }

        //all criteria is present
        if (priceClass != null && rating != null && distance != null && latitude != null && longitude != null) {
            found = restaurantService.getRestaurantsByDistancePriceClassAndRating(longitude, latitude, distance, rating, priceClass);
            if (found.isEmpty()) {
                return new ResponseEntity<>(found.get(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(found.get(), HttpStatus.OK);
            }
        }
        //only distance and lat && long
        if (priceClass == null && rating == null && distance != null && latitude != null && longitude != null) {
            if (restaurantService.getRestaurantsInDistance(longitude, latitude, distance).isEmpty()) {
                return new ResponseEntity<>(restaurantService.getRestaurantsInDistance(longitude, latitude, distance), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(restaurantService.getRestaurantsInDistance(longitude, latitude, distance), HttpStatus.OK);
            }
        }

        //if nothing is present  return all restaurants
        if (priceClass == null && rating == null && distance == null) {
            if (restaurantService.getAllRestaurants().isEmpty()) {
                return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Restaurant>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(found.get(), HttpStatus.CONFLICT);
    }

}
