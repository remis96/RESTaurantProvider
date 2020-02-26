package com.michal.RESTaurant.service.review;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.review.Review;
import com.michal.RESTaurant.entity.review.ReviewEvaluation;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    //REVIEW

    /**
     * @param userId       id of suer posting review
     * @param restaurantId id of restaurant that is being reviewed
     * @return
     */
    CustomResponse addReview(Long userId, Long restaurantId, Review review); //hidden=false

    CustomResponse addReview(String userName, Long restaurantId, Review review);

    ////GET
    Optional<Review> findReviewById(Long id);

    /**
     * @param userId id of user
     * @return all reviews for user with id userId
     */
    Optional<List<Review>> getReviewsForUser(Long userId);

    /**
     * @param restaurantId id of restaurant
     * @return all reviews for restaurant with restaurantId id
     */
    Optional<List<Review>> getReviewsForRestaurant(Long restaurantId);

    /**
     * @return all reviews for all restaurants (for testing purposes)
     */
    List<Review> getAllReviews();


    ////HIDE

    /**
     * as I am prohibited to remove reviews, I simply set them "hidden", each review has boolean hidden atribute
     *
     * @param reviewId id of review
     * @return
     */
    CustomResponse setReviewHidden(Long reviewId);

    ////SHOW

    /**
     * sets review with matching id as shown
     *
     * @param reviewId
     * @return
     */
    CustomResponse setReviewShow(Long reviewId);

    CustomResponse addReviewEvaluation(Long reviewId, Principal principal, ReviewEvaluation reviewEvaluation);

}
