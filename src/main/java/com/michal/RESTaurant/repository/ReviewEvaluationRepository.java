package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.review.ReviewEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewEvaluationRepository extends JpaRepository<ReviewEvaluation, Long> {
}
