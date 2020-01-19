package com.michal.RESTaurant.entity.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michal.RESTaurant.entity.enums.MenuItemType;
import com.michal.RESTaurant.entity.restaurant.Restaurant;

import javax.persistence.*;

@Entity
@Table(name = "menu_item")
public class MenuItem {
    @Column(name = "item_type")
    private MenuItemType itemType;
    @Column(name = "item_price")
    private Float price;
    @Column(name = "item_desription")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonIgnore
    private Restaurant restaurant;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public MenuItemType getItemType() {
        return itemType;
    }

    public void setItemType(MenuItemType itemType) {
        this.itemType = itemType;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
