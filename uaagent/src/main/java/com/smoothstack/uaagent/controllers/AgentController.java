package com.smoothstack.uaagent.controllers;


import com.smoothstack.uaagent.models.Booking;
import com.smoothstack.uaagent.models.Flight;
import com.smoothstack.uaagent.models.Passenger;
import com.smoothstack.uaagent.models.User;
import com.smoothstack.uaagent.security.jwt.JwtRequest;
import com.smoothstack.uaagent.security.jwt.JwtResponse;
import com.smoothstack.uaagent.security.jwt.JwtTokenUtil;
import com.smoothstack.uaagent.security.jwt.JwtUserDetailsService;
import com.smoothstack.uaagent.security.models.UserDTO;
import com.smoothstack.uaagent.services.AgentService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class AgentController {

    Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    AgentService agentService;

    private User user;


    @Timed("authenticate")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        user = agentService.getUserByUsername(authenticationRequest.getUsername());
        agentService.setUser(user);
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

    @Timed("flights.get.all")
    @RequestMapping(path = "utopia/airlines/flights/all", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Flight> getFlights() { return agentService.getFlights(); }

    @Timed("flights.get.available")
    @RequestMapping(path = "utopia/airlines/flights/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Flight> getAvailableFlights() { return agentService.getAvailableFlights(); }

    @Timed("flights.get.id")
    @RequestMapping(path = "utopia/airlines/flights/{flightId}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public Flight getFlightById(@PathVariable Integer flightId) { return agentService.getFlightsById(flightId); }

    @Timed("flights.get.daterange")
    @RequestMapping(path = "utopia/airlines/flights/{time1}/{time2}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Flight> getFlightsByDateRange(@PathVariable String time1, @PathVariable String time2) {
        System.out.println(LocalDateTime.parse(time1));
        return agentService.getFlightsBetweenDates(LocalDateTime.parse(time1), LocalDateTime.parse(time2));
    }

    @Timed("bookings.get.all")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Booking> getBookings() { return agentService.getBookings(); }


    /*commented out code beneath this function maps to the same path
    * So the function below is meant to find the right mapping and use the appropriate booking method
    * */
    @Timed("booking.get")
    @RequestMapping(path = "utopia/airlines/bookings/{way}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Booking> getBookingByWay(@PathVariable String way) {

        if(way.equals("true") || way.equals("false")) {
            return agentService.getBookingByIsActive(Boolean.parseBoolean(way));
        } else {
            try {
                Integer.parseInt(way);
                System.out.println("here");
                Booking booking = agentService.getBookingById(Integer.parseInt(way));
                if(booking != null) {
                    List<Booking> bookings = new ArrayList<Booking>();
                    bookings.add(booking);
                    return bookings;
                }
                else return null;
            } catch (Exception e) {
                logger.info(e.toString());
            }
            System.out.println("Now Here");
            List<Booking> bookings = agentService.getBookingByConfirmationCode(way);
            if(bookings != null) {
                return bookings;
            }
            else return null;
        }
    }

    @Timed("passengers.get")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public List<Passenger> getPassengers() {
        return agentService.getPassengers();
    }

    @Timed("passengers.get.id")
    @RequestMapping(path = "utopia/airlines/passengers/{id}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public Passenger getPassengersById(@PathVariable Integer id) {
        return agentService.getPassengerById(id);
    }

    @Timed("passengers.post")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.POST, consumes = {"application/json", "application/xml", "plain/text"}, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public void postPassenger(@RequestBody Passenger passenger) {
        agentService.postPassenger(passenger);
    }

    @Timed("passengers.post")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.PATCH, consumes = {"application/json", "application/xml", "plain/text"}, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public void patchPassenger(@RequestBody Passenger passenger) {
        agentService.patchPassenger(passenger);
    }

    @Timed("passengers.delete")
    @RequestMapping(path = "utopia/airlines/passengers/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePassengerById(@PathVariable Integer id) {
        agentService.deletePassenger(id);
    }



}
