package com.michal.RESTaurant.entity.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.RESTaurant.entity.user.DAOUser;

import javax.persistence.*;

@Entity
@Table(name = "review_evaluation")
public class ReviewEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "helped")
    private Boolean helped;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_user_id", referencedColumnName = "id")
    @JsonIgnore
    private DAOUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", referencedColumnName = "id")
    @JsonIgnore
    private Review review;

    private String nameOfUser;

    // <editor-fold defaultstate="collapsed" desc="getters/setters">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHelped() {
        return helped;
    }

    public void setHelped(Boolean helped) {
        this.helped = helped;
    }

    public DAOUser getUser() {
        return user;
    }

    public void setUser(DAOUser user) {
        this.user = user;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }
    // </editor-fold>
}
