package com.michal.RESTaurant.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.RESTaurant.entity.review.Review;
import com.michal.RESTaurant.entity.review.ReviewEvaluation;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant_users")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private String mailAdress;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "user")
    private Set<ReviewEvaluation> reviewEvaluations;

    public DAOUser() {
        this.reviews = new HashSet<>();
        this.reviewEvaluations = new HashSet<>();

    }

    public Set<ReviewEvaluation> getReviewEvaluations() {
        return reviewEvaluations;
    }

    public void setReviewEvaluations(Set<ReviewEvaluation> reviewEvaluations) {
        this.reviewEvaluations = reviewEvaluations;
    }

    public void addReviewEvaluation(ReviewEvaluation evaluation) {
        evaluation.setUser(this);
        evaluation.setNameOfUser(this.getUsername());
        this.reviewEvaluations.add(evaluation);
    }

    public String getMailAdress() {
        return mailAdress;
    }

    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Review> getReviews() {

        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        review.setUser(this);
        review.setNameOfUser(this.getUsername());
        reviews.add(review);
    }

}