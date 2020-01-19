package com.michal.RESTaurant.controller.review;


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.review.Review;
import com.michal.RESTaurant.entity.review.ReviewEvaluation;
import com.michal.RESTaurant.service.review.IReviewService;
import com.michal.RESTaurant.service.user.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController implements IReviewController {
    @Autowired
    IReviewService reviewService;
    @Autowired
    JwtUserDetailsService userService;


    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CustomResponse addReview(Principal principal, @RequestParam Long restaurantId, @RequestBody Review review) {

        return reviewService.addReview(principal.getName(), restaurantId, review);

    }


    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> getAllreviews() {
        //alter response
        if (reviewService.getAllReviews().isEmpty()) {
            return new ResponseEntity<List<Review>>(reviewService.getAllReviews(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Review>>(reviewService.getAllReviews(), HttpStatus.OK);
        }
    }

    @Override
    @RequestMapping(value = "/setHidden", method = RequestMethod.PUT)
    public CustomResponse setReviewHidden(@RequestParam Long id) {
        return reviewService.setReviewHidden(id);

    }

    @Override
    @RequestMapping(value = "/setShow", method = RequestMethod.PUT)
    public CustomResponse setReviewShow(@RequestParam Long id) {
        return reviewService.setReviewShow(id);

    }

    @Override
    @RequestMapping(value = "/getReviewsForUser", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviewsForUser(@RequestParam Long id) {

        return new ResponseEntity<List<Review>>(reviewService.getReviewsForUser(id).get(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/getReviewsForRestaurant", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviewsForRestaurant(@RequestParam Long id) {

        return new ResponseEntity<List<Review>>(reviewService.getReviewsForRestaurant(id).get(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/addReviewEvaluation", method = RequestMethod.POST)
    public CustomResponse addReviewEvaluation(Long reviewId, Principal principal, ReviewEvaluation reviewEvaluation) {

        return reviewService.addReviewEvaluation(reviewId, principal, reviewEvaluation);
    }

    //todo patch evaluation + review patch


}
