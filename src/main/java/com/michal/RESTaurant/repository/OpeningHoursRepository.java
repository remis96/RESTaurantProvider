package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.opening_hours.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
}
