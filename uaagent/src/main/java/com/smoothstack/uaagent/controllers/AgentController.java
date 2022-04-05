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
import org.springframework.http.HttpStatus;
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
import java.util.HashMap;
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

    @Timed("flights.get.dump")
    @RequestMapping(path = "utopia/airlines/flights/all", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getFlights() {
        List<Flight> flights = agentService.getFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Timed("flights.get.available")
    @RequestMapping(path = "utopia/airlines/flights/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getAvailableFlights() {
        List<Flight> flights = agentService.getAvailableFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Timed("flights.get.id")
    @RequestMapping(path = "utopia/airlines/flights/{flightId}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getFlightById(@PathVariable Integer flightId) {
        Flight flight = agentService.getFlightsById(flightId);
        if (flight == null) {
            return new ResponseEntity<>("flight id does not exist", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(flight, HttpStatus.OK);
        }
    }

    @Timed("flights.get.daterange")
    @RequestMapping(path = "utopia/airlines/flights/{time1}/{time2}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getFlightsByDateRange(@PathVariable String time1, @PathVariable String time2) {
        try {
            LocalDateTime.parse(time1);
            LocalDateTime.parse(time2);
        } catch (Exception e) {
            return new ResponseEntity<>("beginning and/or end time can not be parsed", HttpStatus.BAD_REQUEST);
        }
        List<Flight> flights = agentService.getFlightsBetweenDates(LocalDateTime.parse(time1), LocalDateTime.parse(time2));
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @Timed("bookings.get.all")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getBookings() {
        List<Booking> bookings = agentService.getBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @Timed("bookings.get.id")
    @RequestMapping(path = "/utopia/airlines/bookings/{bookingId}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getBookingsById(@PathVariable Integer bookingId) {
        Booking check = agentService.getBookingById(bookingId);
        if(check == null) {
            return new ResponseEntity<>("booking id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("passengers.get.dump")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getPassengers() {
        List<Passenger> passengers = agentService.getPassengers();
        logger.info(passengers.toString(), "returned all passengers");
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @Timed("passengers.get.id")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.GET, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> getPassengersById(@PathVariable Integer passengerId) {
        Passenger passenger = agentService.getPassengerById(passengerId);
        if(passenger == null) {
            return new ResponseEntity<>("passenger id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(passenger, HttpStatus.OK);
        }
    }

    @Timed("passengers.post")
    @RequestMapping(path = "utopia/airlines/passengers", method = RequestMethod.POST, consumes = {"application/json", "application/xml", "plain/text"}, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> postPassenger(@RequestBody Passenger passenger) {
        if(passenger.getId() != null) {
            return new ResponseEntity<>("passenger id included in body", HttpStatus.BAD_REQUEST);
        }
        else if(passenger.getBooking_id() == null) {
            return new ResponseEntity<>("no booking included", HttpStatus.BAD_REQUEST);
        }
        else {
            if(passenger.getBooking_id().getId() == null || passenger.getBooking_id().getIs_active() == null || passenger.getBooking_id().getConfirmation_code() == null) {
                return new ResponseEntity<>("booking has null properties", HttpStatus.BAD_REQUEST);
            }
            else {
                Booking bookingCheck = agentService.getBookingById(passenger.getBooking_id().getId());
                if(bookingCheck == null) {
                    return new ResponseEntity<>("booking id does not exist", HttpStatus.BAD_REQUEST);
                }
                else if(!passenger.getBooking_id().getConfirmation_code().equals(bookingCheck.getConfirmation_code()) || !passenger.getBooking_id().getIs_active().equals(bookingCheck.getIs_active()) || !passenger.getBooking_id().getId().equals(bookingCheck.getId())) {
                    logger.info(String.valueOf(bookingCheck.hashCode()));
                    logger.info(String.valueOf(passenger.getBooking_id().hashCode()));
                    return new ResponseEntity<>("booking can not be modified here", HttpStatus.BAD_REQUEST);
                }
                else {
                    passenger.setBooking_id(bookingCheck);
                    Passenger posted = agentService.postPassenger(passenger);
                    return new ResponseEntity<>(posted, HttpStatus.OK);
                }
            }
        }
    }

    @Timed("passengers.put")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml", "plain/text"}, produces = {"application/json", "application/xml", "plain/text"})
    @ResponseBody
    public ResponseEntity<?> putPassenger(@RequestBody Passenger passenger, @PathVariable Integer passengerId) {
        Passenger check = agentService.getPassengerById(passengerId);
        if(check == null) {
            return new ResponseEntity<>("passenger id does not exist", HttpStatus.BAD_REQUEST);
        }
        else if(passenger.getId() != null) {
            return new ResponseEntity<>("passenger id included in body", HttpStatus.BAD_REQUEST);
        }
        else if(passenger.getBooking_id() == null) {
            return new ResponseEntity<>("no booking included", HttpStatus.BAD_REQUEST);
        }
        else {
            if(passenger.getBooking_id().getId() == null || passenger.getBooking_id().getIs_active() == null || passenger.getBooking_id().getConfirmation_code() == null) {
                return new ResponseEntity<>("booking has null properties", HttpStatus.BAD_REQUEST);
            }
            else {
                Booking bookingCheck = agentService.getBookingById(passenger.getBooking_id().getId());
                if(bookingCheck == null) {
                    return new ResponseEntity<>("booking id does not exist", HttpStatus.BAD_REQUEST);
                }
                else {
                    passenger.setId(check.getId());
                    passenger.setBooking_id(bookingCheck);
                    Passenger posted = agentService.postPassenger(passenger);
                    return new ResponseEntity<>(posted, HttpStatus.OK);
                }
            }
        }
    }

    @Timed("passengers.delete")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.DELETE, produces = {"application/json", "application/xml"})
    @ResponseBody
    public ResponseEntity<?> deletePassenger(@PathVariable Integer passengerId) {
        Passenger passenger = agentService.getPassengerById(passengerId);
        if(passenger == null) {
            return new ResponseEntity<>("passenger id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            //passenger.setBooking_id(null);
            //agentService.postPassenger(passenger);
            agentService.deletePassenger(passenger);
            return new ResponseEntity<>("passenger deleted", HttpStatus.OK);
        }
    }

}
