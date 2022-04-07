package com.smoothstack.ua.controllers;


import com.smoothstack.ua.models.UserRole;
import com.smoothstack.ua.services.UserRoleService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
public class UserRoleController {

    private Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;
    
    @Timed("get.userRoles.dump")
    @RequestMapping(path = "utopia/airlines/userRoles/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUserRoles() {
        logger.info("getting all user roles");
        List<UserRole> roles = userRoleService.getAllUserRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Timed("get.userRoles.id")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUserRolesById(@PathVariable Integer userRoleId) {
        UserRole check = userRoleService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user role does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postUserRoles(@Valid @RequestBody UserRole userRole) {
        UserRole check = userRoleService.getUserRoleByName(userRole.getName());
        if(check != null) {
            logger.info("user role already exists");
            return new ResponseEntity<>("user role already exists", HttpStatus.BAD_REQUEST);
        }
        else if(userRole.getId() != null) {
            logger.info("user role id has been included or is not zero");
            return new ResponseEntity<>("user role id has been included or is not zero", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("user role does not exist");
            UserRole posted = userRoleService.saveUserRole(userRole);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }

    @Timed("put.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putUserRoles(@Valid @RequestBody UserRole userRole, @PathVariable Integer userRoleId) {
        UserRole check = userRoleService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else if(userRole.getId() != null) {
            logger.info("user role has an id inluded");
            return new ResponseEntity<>("user role has id included", HttpStatus.BAD_REQUEST);
        }
        else {
            userRole.setId(userRoleId.longValue());
            UserRole put = userRoleService.saveUserRole(userRole);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.userRoles")
    @RequestMapping(path = "utopia/airlines/userRoles/{userRoleId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUserRole(@PathVariable Integer userRoleId) {
        logger.info(userRoleId.toString(), "user role id argument");
        UserRole check = userRoleService.getUserRoleById(userRoleId.longValue());
        if(check == null) {
            logger.info("user role does not exist");
            return new ResponseEntity<>("user role does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            userRoleService.deleteUserRoleById(userRoleId.longValue());
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }

}
