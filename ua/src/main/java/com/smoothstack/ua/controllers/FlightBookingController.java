package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.models.Flight;
import com.smoothstack.ua.models.FlightBookings;
import com.smoothstack.ua.models.FlightBookingsId;
import com.smoothstack.ua.services.BookingService;
import com.smoothstack.ua.services.FlightBookingService;
import com.smoothstack.ua.services.FlightService;
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
public class FlightBookingController {

    private Logger logger = LoggerFactory.getLogger(FlightBookingController.class);

    @Autowired
    private FlightBookingService flightBookingService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @Timed("get.flightBookings.dump")
    @RequestMapping(path = "utopia/airlines/flightBookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightBookings() {
        List<FlightBookings> flightBookings = flightBookingService.getFlightBookings();
        return new ResponseEntity<>(flightBookings, HttpStatus.OK);
    }

    @Timed("get.flightBookings.ids")
    @RequestMapping(path = "utopia/airlines/flightBookings/{flightId}/{bookingId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getFlightBookingsById(@PathVariable Integer flightId, @PathVariable Integer bookingId) {
        FlightBookings check = flightBookingService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight booking does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight booking does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBookings/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postFlightBookings(@RequestBody FlightBookings flightBooking) {
        FlightBookings check = flightBookingService.getFlightBookingsById(flightBooking.getFlightBookingsId());
        if(check == null) {
            logger.info("flight Booking does not exist");
            Flight flight = flightService.getFlightById(flightBooking.getFlightBookingsId().getFlight_id());
            Booking booking = bookingService.getBookingsById(flightBooking.getFlightBookingsId().getBooking_id());
            if(flight == null || booking == null) {
                logger.info("Either flight and/or booking does not exist");
                return new ResponseEntity<>("either flight and/or booking does not exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight booking appears to be in order");
                FlightBookings posted = flightBookingService.saveFlightBooking(flightBooking);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
        else{
            logger.info("flight booking composite id already exists");
            return new ResponseEntity<>("flight booking composite id already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBookings/{flightId}/{bookingId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putFlightBooking(@RequestBody FlightBookings flightBooking, @PathVariable Integer flightId, @PathVariable Integer bookingId) {
        FlightBookings check = flightBookingService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight bookings does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            Flight flight = flightService.getFlightById(flightBooking.getFlightBookingsId().getFlight_id());
            Booking booking = bookingService.getBookingsById(flightBooking.getFlightBookingsId().getBooking_id());
            if(flight == null || booking == null) {
                logger.info("Either flight and/or booking does not exist");
                return new ResponseEntity<>("either flight and/or booking does not exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("flight booking appears to be in order");
                FlightBookings posted = flightBookingService.saveFlightBooking(flightBooking);
                flightBookingService.deleteFlightBooking(check);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }

    }


    @Timed("delete.flightBookings")
    @RequestMapping(path = "utopia/airlines/flightBooking/{flightId}/{bookingId}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteFlightBookingsById(@PathVariable Integer flightId, @PathVariable Integer bookingId) {
        FlightBookings check = flightBookingService.getFlightBookingsById(new FlightBookingsId(flightId, bookingId));
        if(check == null) {
            logger.info("flight booking does not exist");
            return new ResponseEntity<>("flight booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("flight booking does exist");
            flightBookingService.deleteFlightBooking(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
