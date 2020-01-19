package com.michal.RESTaurant.controller.user;


import com.michal.RESTaurant.entity.user.DAOUser;
import com.michal.RESTaurant.service.user.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController implements IUserController {
    @Autowired
    JwtUserDetailsService userService;


    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<DAOUser>> getAllUsers() {
        return new ResponseEntity<List<DAOUser>>(userService.getAllUsers(), HttpStatus.OK);
    }


}
