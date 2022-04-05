package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.User;
import com.smoothstack.ua.security.jwt.JwtRequest;
import com.smoothstack.ua.security.jwt.JwtResponse;
import com.smoothstack.ua.security.jwt.JwtTokenUtil;
import com.smoothstack.ua.security.jwt.JwtUserDetailsService;
import com.smoothstack.ua.security.models.UserDTO;
import com.smoothstack.ua.services.UserService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("/utopia/airlines")
public class CredentialsController {

    private Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private User user;


    @Timed("authenticate")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        user = userService.getUserByUsername(authenticationRequest.getUsername());
        this.user = user;
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Timed("register")
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
