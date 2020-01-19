package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.menu.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
