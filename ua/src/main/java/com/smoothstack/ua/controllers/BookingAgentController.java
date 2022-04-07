package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.BookingAgent;
import com.smoothstack.ua.models.BookingAgentId;
import com.smoothstack.ua.services.BookingAgentService;
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
public class BookingAgentController {

    private Logger logger = LoggerFactory.getLogger(BookingAgentController.class);

    @Autowired
    private BookingAgentService bookingAgentService;

    @Timed("get.bookingAgents.dump")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingAgents() {
        return new ResponseEntity<>(bookingAgentService.getAllBookingAgents(), HttpStatus.OK);
    }

    @Timed("get.bookingAgents.id")
    @RequestMapping(path = "utopia/airlines/bookingAgents/{bookingId}/{userId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingAgentById(@PathVariable Integer bookingId, @PathVariable Integer userId) {
        BookingAgentId bookingAgentId = new BookingAgentId(bookingId, userId);
        BookingAgent bookingAgent = bookingAgentService.getBookingAgentById(bookingAgentId);
        if(bookingAgent == null) {
            logger.info("booking agent does not exist");
            return new ResponseEntity<>("booking agent does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info(bookingAgent.toString(), "booking agent returned");
            return new ResponseEntity<>(bookingAgent, HttpStatus.OK);
        }
    }


    @Timed("post.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> saveBookingAgents(@RequestBody BookingAgent bookingAgent) {
        BookingAgent check = bookingAgentService.getBookingAgentById(bookingAgent.getBookingAgentId());

        if(check == null) {
            logger.info("booking agent does not exist");
            BookingAgent posted = bookingAgentService.saveBookingAgent(bookingAgent);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
        else {
            logger.info("booking agent already exists");
            return new ResponseEntity<>("booking agent already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgents/{bookingId}/{flightId}", method = RequestMethod.DELETE, consumes = {"*/*","application/json", "application/xml"})
    public ResponseEntity<?> deleteBookingAgent(@PathVariable Integer bookingId, @PathVariable Integer flightId) {
        BookingAgent check = bookingAgentService.getBookingAgentById(new BookingAgentId(bookingId, flightId));
        if(check != null) {
            logger.info("booking agent exists");
            bookingAgentService.deleteBookingAgent(check);
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
        else {
            logger.info("booking agent does not exist");
            return new ResponseEntity<>("booking agent id does not exist", HttpStatus.BAD_REQUEST);
        }
    }
}
