package com.michal.RESTaurant.controller.user;


import com.michal.RESTaurant.entity.user.DAOUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface IUserController {

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<List<DAOUser>> getAllUsers();


}
