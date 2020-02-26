package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Override
    Optional<Restaurant> findById(Long aLong);

    @Query("SELECT r FROM Restaurant r  WHERE r.rating >= ?1")
    Optional<List<Restaurant>> findByRating(Float rating);

    @Query("SELECT r FROM Restaurant r  WHERE r.priceClass >= ?1")
    Optional<List<Restaurant>> findByPriceClass(Float rating);

    @Query("SELECT r FROM Restaurant r  WHERE r.rating >= ?1 AND r.priceClass >= ?2")
    Optional<List<Restaurant>> findByPriceClassAndRating(Float rating, Float priceClass);
}
