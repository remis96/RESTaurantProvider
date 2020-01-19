package com.michal.RESTaurant.repository;


import com.michal.RESTaurant.entity.user.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<DAOUser, Long> {

    Optional<DAOUser> findByUsername(String username);

    Optional<DAOUser> findByMailAdress(String mailAdress);

}