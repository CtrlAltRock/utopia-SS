package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.User;
import com.smoothstack.ua.models.UserRole;
import com.smoothstack.ua.services.UserRoleService;
import com.smoothstack.ua.services.UserService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Timed("get.users.dump")
    @RequestMapping(path = "utopia/airlines/users/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUsers() {
        logger.info("getting all users");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Timed("get.users.id")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> getUsersById(@PathVariable Integer userId) {
        logger.info(userId.toString(), "user id argument");
        User check = userService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.users")
    @RequestMapping(path = "utopia/airlines/users/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postUsers(@RequestBody User user) {
        logger.info(user.toString(), "user body argument");
        if(user.getId() != 0) {
            logger.info("user has an id other than zero");
            return new ResponseEntity<>("user has an id other than zero", HttpStatus.BAD_REQUEST);
        }
        else {
            UserRole userRoleCheck = userRoleService.getUserRoleById(user.getUserRole().getId());
            logger.info(userRoleCheck.toString(), "db role to check against");

            if(userRoleCheck == null) {
                logger.info("trying to change UserRole from user post");
                return new ResponseEntity<>("trying to change UserRole from user post", HttpStatus.BAD_REQUEST);
            }
            else if(!user.getUserRole().equals(userRoleCheck)){
                logger.info("trying to create a new user role");
                return new ResponseEntity<>("trying to create a new user role", HttpStatus.BAD_REQUEST);
            }
            else {
                logger.info("user seems fine to post");
                User posted = userService.saveUser(user);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("put.users")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putUsers(@RequestBody User user, @PathVariable Integer userId) {
        logger.info(user.toString(), "user argument");
        logger.info(userId.toString(), "user id argument");
        User check = userService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else if(user.getId() != 0) {
            logger.info("user has an id other than zero");
            return new ResponseEntity<>("user has an id other than zero", HttpStatus.BAD_REQUEST);
        }
        else{
            UserRole userRoleCheck = userRoleService.getUserRoleById(user.getUserRole().getId());
            if(userRoleCheck == null) {
                logger.info("user role does not exist");
                return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
            }
            else if(!userRoleCheck.equals(user.getUserRole())) {
                logger.info("not allowed to override an existing user role");
                return new ResponseEntity<>("not allowed to override an existing user role", HttpStatus.BAD_REQUEST);
            }
            else {
                logger.info("user seems fine to post");
                user.setId(userId.longValue());
                User posted = userService.saveUser(user);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("delete.users")
    @RequestMapping(path = "utopia/airlines/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUsersById(@PathVariable Integer userId) {
        logger.info(userId.toString(), "user id argument");
        User check = userService.getUserById(userId);
        if(check == null) {
            logger.info("user does not exist");
            return new ResponseEntity<>("user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            check.setUserRole(null);
            userService.deleteUser(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
