package com.smoothstack.uauser.controllers;

import com.smoothstack.uauser.models.Flight;
import com.smoothstack.uauser.models.User;
import com.smoothstack.uauser.repos.FlightBookingsRepository;
import com.smoothstack.uauser.repos.FlightRepository;
import com.smoothstack.uauser.security.jwt.JwtRequest;
import com.smoothstack.uauser.security.jwt.JwtResponse;
import com.smoothstack.uauser.security.jwt.JwtTokenUtil;
import com.smoothstack.uauser.security.jwt.JwtUserDetailsService;
import com.smoothstack.uauser.security.models.UserDTO;
import com.smoothstack.uauser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightBookingsRepository flightBookingsRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private User user;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        //System.out.println(authenticationRequest.getUsername());
        user = userService.getUserByUsername(authenticationRequest.getUsername());
//        System.out.println(user);
        userService.setUser(user);
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"})
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


    @RequestMapping(path = "/utopia/airlines/flights/", method = RequestMethod.GET, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"} )
    public ResponseEntity<?> getAvailableFlights() {
        List<Flight> flights = userService.getAvailableFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @RequestMapping(path = "/utopia/airlines/myflights/", method = RequestMethod.GET, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"} )
    public ResponseEntity<?> getUserFlights() {
        List<Flight> flights = userService.getUserFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }


    @RequestMapping(path = "/utopia/airlines/flights/{flightId}", method = RequestMethod.POST, consumes = {"application/xml", "application/json"}, produces = {"application/xml", "application/json"} )
    public ResponseEntity<?> postUserTickets(@PathVariable Integer flightId) {
        Flight flight = userService.getFlightsById(flightId);
        if(flight != null) {
            userService.addUserToFlight(flight);
            flight = userService.getFlightsById(flightId);
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Flight Not Found", HttpStatus.BAD_REQUEST);
    }








}
