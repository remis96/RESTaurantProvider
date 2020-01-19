package com.michal.RESTaurant.entity.opening_hours;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.RESTaurant.entity.restaurant.Restaurant;

import javax.persistence.*;

@Entity
@Table(name = "exception_date")
public class ExceptionDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "day_of_month")
    private Integer dayOfMonth;
    @Column(name = "month_of_year")
    private Integer monthOfYear;
    @Column(name = "year")
    private Integer year;
    @Column(name = "opening_hour")
    private Integer openingHour;
    @Column(name = "opening_minute")
    private Integer openingMinute;
    @Column(name = "closing_minute")
    private Integer closingMinute;
    @Column(name = "closing_hour")
    private Integer closingHour;
    @Column(name = "closed")
    private Boolean closed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonIgnore
    private Restaurant restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(Integer monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHours(Integer openingHour) {
        this.openingHour = openingHour;
    }

    public Integer getOpeningMinute() {
        return openingMinute;
    }

    public void setOpeningMinute(Integer openingMinute) {
        this.openingMinute = openingMinute;
    }

    public Integer getClosingMinute() {
        return closingMinute;
    }

    public void setClosingMinute(Integer closingMinute) {
        this.closingMinute = closingMinute;
    }

    public Integer getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
    }
}
