package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.User;
import com.smoothstack.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "utopia/airlines/get/user/{userId}", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(path = "utopia/airlines/post/user/", method = RequestMethod.POST, consumes = {"application/xml", "application/json"})
    public void saveNewUser(@RequestBody User user) {
        try{
            userService.saveUser(user);
        } catch (NoSuchElementException e) {

        }
    }



}
