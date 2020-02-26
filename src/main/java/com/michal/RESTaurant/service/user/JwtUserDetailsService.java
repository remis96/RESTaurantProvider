package com.michal.RESTaurant.service.user;
/*
JWTUserDetailsService implements the Spring Security UserDetailsService interface.
It overrides the loadUserByUsername for fetching user details from the database using the username.
The Spring Security Authentication Manager calls this method for getting the user details from the database
when authenticating the user details provided by the user. Here we are getting the user details from a hardcoded User List.
 */


import com.michal.RESTaurant.config.CustomResponse;
import com.michal.RESTaurant.entity.enums.TypeOfUser;
import com.michal.RESTaurant.entity.user.DAOUser;
import com.michal.RESTaurant.entity.user.UserDTO;
import com.michal.RESTaurant.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DAOUser> user = userDao.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
                new ArrayList<>());
    }

    public Optional<TypeOfUser> getTypeOfUser(String name) {
        TypeOfUser type;
        Optional<DAOUser> user = userDao.findByUsername(name);
        if (user.isPresent()) {
            type = user.get().getTypeOfUser();
            return Optional.of(type);
        } else {
            return Optional.empty();
        }

    }

    public CustomResponse save(UserDTO user) {
        CustomResponse response = new CustomResponse(HttpStatus.CONFLICT, "Bad credentials");
        if (!validate(user.getMailAdress())) {
            response.setMessage("Invalid mail adress");
            return response;
        }
        if (userDao.findByUsername(user.getUsername()).isPresent()) {
            response.setMessage("Nickname taken");
            return response;
        }
        if (userDao.findByMailAdress(user.getMailAdress()).isPresent()) {
            response.setMessage("Mail adress is already in use");
            return response;
        }

        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setMailAdress(user.getMailAdress());
        newUser.setTypeOfUser(user.getTypeOfUser());
        userDao.save(newUser);
        response.setMessage("Sucess, account " + newUser.getUsername() + " " + newUser.getMailAdress() + " created");
        response.setStatus(HttpStatus.OK);
        return response;
    }

    public List<DAOUser> getAllUsers() {
        return userDao.findAll();
    }


}