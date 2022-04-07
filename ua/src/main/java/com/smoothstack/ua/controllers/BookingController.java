package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.services.BookingService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class BookingController {

    private Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @Timed("get.bookings.dump")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookings() {
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }

    @Timed("get.bookings.id")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookings(@PathVariable Integer id) {
        Booking check = bookingService.getBookingsById(id);
        if(check == null) {
            logger.info("booking does not exist");
            return new ResponseEntity<>("booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.bookings")
    @RequestMapping(path = "utopia/airlines/bookings/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookings(@RequestBody Booking booking) {
        Booking posted = bookingService.saveBooking(booking);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @Timed("put.booking")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBooking(@RequestBody Booking booking, @PathVariable Integer id) {
        Booking check = bookingService.getBookingsById(id);
        if(check == null) {
            logger.info("id of booking does not exist");
            return new ResponseEntity<>("id of booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking exists");
            check.setIs_active(booking.getIs_active());
            check.setConfirmation_code(booking.getConfirmation_code());
            Booking put = bookingService.saveBooking(check);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.booking")
    @RequestMapping(path = "utopia/airlines/bookings/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBookingById(@PathVariable Integer id) {
        Booking check = bookingService.getBookingsById(id);

        if(check == null) {
            logger.info("booking does not exist");
            return new ResponseEntity<>("booking does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking does exist");
            bookingService.deleteBookingById(id);
            return new ResponseEntity<>(check, HttpStatus.OK);
        }

    }



}
