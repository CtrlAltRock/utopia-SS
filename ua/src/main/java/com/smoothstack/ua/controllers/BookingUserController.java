package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.BookingUser;
import com.smoothstack.ua.models.BookingUserId;
import com.smoothstack.ua.services.BookingUserService;
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
public class BookingUserController {

    private Logger logger = LoggerFactory.getLogger(BookingUserController.class);

    @Autowired
    private BookingUserService bookingUserService;

    @Timed("get.bookingUsers.dump")
    @RequestMapping(path = "utopia/airlines/bookingUsers/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingUsers() {
        List<BookingUser> bookingUsers = bookingUserService.getAllBookingUsers();
        logger.info(bookingUsers.toString(),"getting all bookingUsers");
        return new ResponseEntity<>(bookingUsers, HttpStatus.OK);
    }

    @Timed("get.bookingUsers.id")
    @RequestMapping(path = "utopia/airlines/bookingUsers/{bookingId}/{userId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingUserById(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        BookingUser check = bookingUserService.getBookingUserById(new BookingUserId(bookingId, userId));
        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking user does exist");
            return new ResponseEntity<>(check, HttpStatus.OK);
        }
    }

    @Timed("post.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUsers/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postBookingUsers(@RequestBody BookingUser bookingUser) {
        BookingUser check = bookingUserService.getBookingUserById(bookingUser.getBookingUserId());
        if(check == null) {
            logger.info("booking user does not exist");
            BookingUser posted = bookingUserService.saveBookingUser(bookingUser);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
        else{
            logger.info("booking user already exists");
            return new ResponseEntity<>("booking user already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUsers/{bookingId}/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingUser(@RequestBody BookingUser bookingUser, @PathVariable Integer bookingId, @PathVariable Integer userId) {
        BookingUser check = bookingUserService.getBookingUserById(new BookingUserId(bookingId, userId));
        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking user does exist");
            bookingUser.setBookingUserId(new BookingUserId(bookingId, userId));
            BookingUser put = bookingUserService.saveBookingUser(bookingUser);
            return new ResponseEntity<>(put, HttpStatus.OK);

        }
    }

    @Timed("delete.bookingUsers")
    @RequestMapping(path = "utopia/airlines/bookingUsers/{bookingId}/{userId}", method = RequestMethod.DELETE, consumes = {"*/*" ,"application/json", "application/xml"})
    public ResponseEntity<?> deleteBookingUser(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        BookingUser check = bookingUserService.getBookingUserById(new BookingUserId(bookingId, userId));
        if(check == null) {
            logger.info("booking user does not exist");
            return new ResponseEntity<>("booking user does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking user does exist");
            bookingUserService.deleteBookingUser(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
