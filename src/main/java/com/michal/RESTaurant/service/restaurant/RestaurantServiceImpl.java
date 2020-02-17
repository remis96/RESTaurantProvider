package com.michal.RESTaurant.service.restaurant;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.GeoCoordinates;
import com.michal.RESTaurant.entity.enums.Day;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.opening_hours.OpeningHours;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.repository.ExceptionDateRepository;
import com.michal.RESTaurant.repository.MenuItemRepository;
import com.michal.RESTaurant.repository.OpeningHoursRepository;
import com.michal.RESTaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    OpeningHoursRepository openingHoursRepository;
    @Autowired
    ExceptionDateRepository exceptionDateRepository;
    @Autowired
    DistanceEvaluator distanceEvaluator;

    @Override
    public Set<MenuItem> getMenuForRestaurant(Long restaurantId) {

        return findrestaurantById(restaurantId).get().getMenuItems();
    }

    @Override
    public CustomResponse addRestaurant(Restaurant restaurant) {
        if (restaurantRepository.exists(Example.of(restaurant))) {
            return new CustomResponse(HttpStatus.CONFLICT, "Dany podnik uz existuje");
        } else {
            restaurant.initializeWeek();
            restaurantRepository.save(restaurant);
            openingHoursRepository.saveAll(restaurant.getOpeningHours());


            return new CustomResponse(HttpStatus.CREATED, "Podnik uspesne pridany");
        }
    }

    @Override
    public CustomResponse addMenuItem(Long restaurantId, MenuItem menuItem) {
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "No such restauratn exists");
        Optional<Restaurant> res = findrestaurantById(restaurantId);
        if (res.isPresent()) {
            res.get().addMenuItem(menuItem);
            menuItemRepository.save(menuItem);
            restaurantRepository.save(res.get());
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Menu item added sucessfully");
        }
        return response;
    }

    @Override
    public void initializeOpeningHours() {
        HashMap<Day, OpeningHours> hours = new HashMap<>();
    }

    @Override
    public CustomResponse addExceptionDate(Long restaurantId, ExceptionDate date) {
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "No such restaurant exists");
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            restaurant.get().addExceptionDate(date);
            restaurantRepository.save(restaurant.get());
            exceptionDateRepository.save(date);
            response.setMessage("Exception date added sucessfully");
            response.setStatus(HttpStatus.CREATED);

        }
        return response;

    }

    @Override
    public void initializeOpeningHoursRestaurant(Restaurant res) {
        res.initializeWeek();
        restaurantRepository.save(res);
        openingHoursRepository.saveAll(res.getOpeningHours());
    }

    @Override
    public List<Restaurant> getRestaurantsInDistance(Double sourceLong, Double sourceLat, Double distanceInMeters) throws InterruptedException, IOException {
        GeoCoordinates source = new GeoCoordinates();
        source.setLongitude(sourceLong);
        source.setLatitude(sourceLat);
        return distanceEvaluator.getRestaurantsInDistance(source, distanceInMeters, getAllRestaurants());
    }

    @Override
    public Optional<Restaurant> findrestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
