package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.models.Passenger;
import com.smoothstack.ua.services.BookingService;
import com.smoothstack.ua.services.PassengerService;
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
public class PassengerController {

    private Logger logger = LoggerFactory.getLogger(PassengerController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;


    @Timed("get.passengers.dump")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getPassengers() {
        List<Passenger> passengers = passengerService.getPassengers();
        logger.info(passengers.toString(), "passengers from db");
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @Timed("get.passengers.id")
    @RequestMapping(path = "utopia/airline/passengers/{passengerId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getPassengerById(@PathVariable Integer passengerId) {
        Passenger check = passengerService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger does not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }


    @Timed("post.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postPassengers(@RequestBody Passenger passenger) {
        if(passenger.getBooking_id() == null || passenger.getId() != null) {
            logger.info("either booking is not provided and/or id has been provided");
            return new ResponseEntity<>("either booking is not provided and/or id has been provided", HttpStatus.BAD_REQUEST);
        }
        else {
            Booking check = bookingService.getBookingsById(passenger.getBooking_id().getId());
            if(check == null) {
                logger.info("booking doesn't exist");
                return new ResponseEntity<>("booking doesn't exist", HttpStatus.BAD_REQUEST);
            }
            else{
                logger.info("passenger seems good to post");
                Passenger posted = passengerService.savePassenger(passenger);
                return new ResponseEntity<>(posted, HttpStatus.OK);
            }
        }
    }

    @Timed("put.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putPassengers(@RequestBody Passenger passenger, @PathVariable Integer passengerId) {
        Passenger check = passengerService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger does not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            if(check.getBooking_id().getId() != passenger.getBooking_id().getId()) {
                logger.info("checking updated booking id if it exists");
                Booking booking = bookingService.getBookingsById(passenger.getBooking_id().getId());
                if(booking == null) {
                    logger.info("updated booking  does not exist");
                    return new ResponseEntity<>("booking being updated does not exist", HttpStatus.BAD_REQUEST);
                }
            }
            logger.info("passenger to update seems to be in order");
            passenger.setId(passengerId);
            Passenger posted = passengerService.savePassenger(passenger);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }


    @Timed("delete.passenger")
    @RequestMapping(path = "utopia/airlines/passengers/{passengerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePassengerById(@PathVariable Integer passengerId) {
        Passenger check = passengerService.getPassengerById(passengerId);
        if(check == null) {
            logger.info("passenger doe not exist");
            return new ResponseEntity<>("passenger does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("passenger does exist");
            check.setBooking_id(null);
            Passenger update = passengerService.savePassenger(check);
            bookingService.deleteBookingById(check.getId());
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
