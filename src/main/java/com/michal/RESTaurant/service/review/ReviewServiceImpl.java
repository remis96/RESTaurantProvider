package com.michal.RESTaurant.service.review;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.entity.review.Review;
import com.michal.RESTaurant.entity.review.ReviewEvaluation;
import com.michal.RESTaurant.entity.user.DAOUser;
import com.michal.RESTaurant.repository.RestaurantRepository;
import com.michal.RESTaurant.repository.ReviewEvaluationRepository;
import com.michal.RESTaurant.repository.ReviewRepository;
import com.michal.RESTaurant.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserDao userRepository;
    @Autowired
    ReviewEvaluationRepository reviewEvaluationRepository;


    @Override
    public CustomResponse addReview(Long userId, Long restaurantId, Review review) {
        Optional<DAOUser> user = userRepository.findById(userId);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "No such restaurant or customer exists");

        if (user.isPresent() && restaurant.isPresent()) {
            user.get().addReview(review);
            restaurant.get().addReview(review);
            reviewRepository.save(review);
            restaurantRepository.save(restaurant.get());
            userRepository.save(user.get());
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Review added sucessfully");

        }
        return response;

    }

    @Override
    public CustomResponse addReview(String userName, Long restaurantId, Review review) {
        Optional<DAOUser> user = userRepository.findByUsername(userName);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "No such restaurant or customer exists");

        if (user.isPresent() && restaurant.isPresent()) {
            user.get().addReview(review);
            restaurant.get().addReview(review);
            restaurant.get().calculatePriceClass();
            restaurant.get().calculateRating();
            reviewRepository.save(review);
            restaurantRepository.save(restaurant.get());
            userRepository.save(user.get());
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Review added sucessfully");
        }
        return response;
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Optional<List<Review>> getReviewsForUser(Long userId) {

        Optional<DAOUser> user = userRepository.findById(userId);
        ArrayList<Review> review = new ArrayList<>();
        user.ifPresent(restaurantUser -> review.addAll(restaurantUser.getReviews()));
        return Optional.of(review);


    }

    @Override
    public Optional<List<Review>> getReviewsForRestaurant(Long restaurantId) {

        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        ArrayList<Review> review = new ArrayList<>();
        restaurant.ifPresent(value -> review.addAll(value.getReviews()));
        return Optional.of(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();

    }

    @Override
    public CustomResponse setReviewHidden(Long reviewId) {
        Optional<Review> review = findReviewById(reviewId);
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "This review does not exist");
        review.ifPresent(value -> {
            value.setHidden(true);
            reviewRepository.save(value);
            response.setMessage("Review set hidden");
            response.setStatus(HttpStatus.OK);
        });
        return response;
    }

    @Override
    public CustomResponse setReviewShow(Long reviewId) {
        Optional<Review> review = findReviewById(reviewId);
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "This review does not exist");
        review.ifPresent(value -> {
            value.setHidden(false);
            reviewRepository.save(value);
            response.setMessage("Review set shown");
            response.setStatus(HttpStatus.OK);
        });
        return response;
    }

    @Override
    public CustomResponse addReviewEvaluation(Long reviewId, Principal principal, ReviewEvaluation reviewEvaluation) {
        CustomResponse response = new CustomResponse(HttpStatus.NO_CONTENT, "This review does not exist");
        Optional<Review> review = reviewRepository.findById(reviewId);
        Optional<DAOUser> user = userRepository.findByUsername(principal.getName());
        if (review.isPresent() && user.isPresent()) {

            user.get().addReviewEvaluation(reviewEvaluation);
            review.get().addEvaluation(reviewEvaluation);
            userRepository.save(user.get());
            reviewRepository.save(review.get());
            reviewEvaluationRepository.save(reviewEvaluation);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Review evaluated sucessfully");
        }
        return response;
    }

}
