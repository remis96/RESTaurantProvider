package com.michal.RESTaurant.entity.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.RESTaurant.entity.enums.PriceClass;
import com.michal.RESTaurant.entity.restaurant.Restaurant;
import com.michal.RESTaurant.entity.user.DAOUser;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "price_class")
    private PriceClass priceClass;
    @Column(name = "rating")
    private Float rating;
    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date dateOfPosting;
    @Column(name = "hidden")
    private boolean hidden;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_user_id", referencedColumnName = "id")
    @JsonIgnore
    private DAOUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonIgnore
    private Restaurant restaurant;
    private String nameOfUser;
    @OneToMany(mappedBy = "review")
    private Set<ReviewEvaluation> reviewEvaluations;

    public Review() {
        this.dateOfPosting = new Date();
        this.dateOfPosting.setTime(System.currentTimeMillis());

    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Set<ReviewEvaluation> getReviewEvaluations() {
        return reviewEvaluations;
    }

    public void setReviewEvaluations(Set<ReviewEvaluation> reviewEvaluations) {
        this.reviewEvaluations = reviewEvaluations;
    }

    public PriceClass getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(PriceClass priceClass) {
        this.priceClass = priceClass;
    }

    public void addEvaluation(ReviewEvaluation evaluation) {
        evaluation.setReview(this);
        this.reviewEvaluations.add(evaluation);
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateOfPosting() {
        return dateOfPosting;
    }

    public void setDateOfPosting(Date dateOfPosting) {
        this.dateOfPosting = dateOfPosting;
    }

    public DAOUser getUser() {
        return user;
    }

    public void setUser(DAOUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
