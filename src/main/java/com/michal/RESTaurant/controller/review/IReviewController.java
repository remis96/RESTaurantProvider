package com.michal.RESTaurant.controller.review;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.review.Review;
import com.michal.RESTaurant.entity.review.ReviewEvaluation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

public interface IReviewController {
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    CustomResponse addReview(Principal principal, @RequestParam Long restaurantId, @RequestBody Review revies);

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<List<Review>> getAllreviews();

    @RequestMapping(value = "/setHidden", method = RequestMethod.PUT)
    CustomResponse setReviewHidden(@RequestParam Long id);

    @RequestMapping(value = "/setShow", method = RequestMethod.PUT)
    CustomResponse setReviewShow(@RequestParam Long id);

    @RequestMapping(value = "/getReviewsForUser", method = RequestMethod.GET)
    ResponseEntity<List<Review>> getReviewsForUser(@RequestParam Long id);

    @RequestMapping(value = "/getReviewsForRestaurant", method = RequestMethod.GET)
    ResponseEntity<List<Review>> getReviewsForRestaurant(@RequestParam Long id);

    @RequestMapping(value = "/addEvaluation", method = RequestMethod.POST)
    CustomResponse addReviewEvaluation(@RequestParam Long reviewId, Principal principal, @RequestBody ReviewEvaluation reviewEvaluation);
}
