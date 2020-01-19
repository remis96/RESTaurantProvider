package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {


}
