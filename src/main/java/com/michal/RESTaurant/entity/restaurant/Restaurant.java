package com.michal.RESTaurant.entity.restaurant;

import com.michal.RESTaurant.entity.enums.Day;
import com.michal.RESTaurant.entity.menu.MenuItem;
import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import com.michal.RESTaurant.entity.opening_hours.OpeningHours;
import com.michal.RESTaurant.entity.review.Review;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Column(name = "price_class")
    private Float priceClass;

    @Column(name = "rating")
    private Float rating;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private Integer phoneNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "street")
    private String street;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "hidden")
    private Boolean hidden;

    @OneToMany(mappedBy = "restaurant")
    private Set<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "restaurant")
    private List<OpeningHours> openingHours;

    @OneToMany(mappedBy = "restaurant")
    private List<ExceptionDate> exceptionDates;

    // <editor-fold defaultstate="collapsed" desc="getters/setters">
    public Restaurant() {
        this.reviews = new HashSet<>();
        this.openingHours = new ArrayList<>();
        this.exceptionDates = new ArrayList<>();
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addExceptionDate(ExceptionDate exceptionDate) {
        this.exceptionDates.add(exceptionDate);
        exceptionDate.setRestaurant(this);
    }

    public List<ExceptionDate> getExceptionDates() {
        return exceptionDates;
    }

    public void setExceptionDates(List<ExceptionDate> exceptionDates) {
        this.exceptionDates = exceptionDates;
    }

    public List<OpeningHours> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHours> openingHours) {
        this.openingHours = openingHours;
    }

    public void setOpeningHours(Integer hour, Integer minute) {
        for (OpeningHours op : openingHours) {
            op.setOpenHour(hour);
            op.setOpenMinute(minute);
        }
    }

    public void setClosingHours(Integer hour, Integer minute) {
        for (OpeningHours op : openingHours) {
            op.setCloseHour(hour);
            op.setCloseMinute(minute);
        }
    }

    public void setOpeningHoursForDay(Day day, Integer hour, Integer minute) {
        for (OpeningHours op : openingHours) {
            if (op.getDayOfWeek() == day) {
                op.setOpenHour(hour);
                op.setOpenMinute(minute);
                break;
            }
        }
    }

    public void setClosingHoursForDay(Day day, Integer hour, Integer minute) {
        for (OpeningHours op : openingHours) {
            if (op.getDayOfWeek() == day) {
                op.setCloseHour(hour);
                op.setCloseMinute(minute);
                break;
            }
        }
    }

    public void calculatePriceClass() {
        float calculated = 0;
        for (Review review : reviews) {
            calculated += review.getPriceClass();
        }
        this.priceClass = calculated / reviews.size();
    }

    public void calculateRating() {
        float calculated = 0;
        for (Review review : reviews) {
            calculated += review.getRating();
        }
        this.rating = calculated / reviews.size();
    }

    public Float getPriceClass() {
        return priceClass;
    }

    public void setPriceClass(Float priceClass) {
        this.priceClass = priceClass;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItem.setRestaurant(this);
        this.menuItems.add(menuItem);
    }

    public void initializeWeek() {
        OpeningHours monday = new OpeningHours();
        monday.setDayOfWeek(Day.MONDAY);
        OpeningHours tuesday = new OpeningHours();
        tuesday.setDayOfWeek(Day.TUESDAY);
        OpeningHours wednesday = new OpeningHours();
        wednesday.setDayOfWeek(Day.WEDNESDAY);
        OpeningHours thursday = new OpeningHours();
        thursday.setDayOfWeek(Day.THURSDAY);
        OpeningHours friday = new OpeningHours();
        friday.setDayOfWeek(Day.FRIDAY);
        OpeningHours saturday = new OpeningHours();
        saturday.setDayOfWeek(Day.SATURDAY);
        OpeningHours sunday = new OpeningHours();
        sunday.setDayOfWeek(Day.SUNDAY);
        openingHours.add(monday);
        openingHours.add(tuesday);
        openingHours.add(wednesday);
        openingHours.add(thursday);
        openingHours.add(friday);
        openingHours.add(saturday);
        openingHours.add(sunday);
        for (OpeningHours op : openingHours) {
            op.setRestaurant(this);
        }
    }


    public Set<Review> getReviews() {

        return this.reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        review.setRestaurant(this);
        this.reviews.add(review);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
    // </editor-fold>
}
