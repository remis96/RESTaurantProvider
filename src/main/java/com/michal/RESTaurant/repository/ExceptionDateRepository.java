package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.opening_hours.ExceptionDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionDateRepository extends JpaRepository<ExceptionDate, Long> {
}
