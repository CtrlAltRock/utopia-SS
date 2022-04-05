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
        return new ResponseEntity<>(bookingAgentService.getALlBookingAgents(), HttpStatus.OK);
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
            logger.info("booking agent already exists");
            return new ResponseEntity<>("booking agent already exists", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking agent does not exist");
            BookingAgent posted = bookingAgentService.saveBookingAgent(bookingAgent);
            return new ResponseEntity<>(posted, HttpStatus.OK);
        }
    }

/*
    @Timed("post.bookingAgent")
    @RequestMapping(path = "utopia/airlines/bookingAgent/{bookingId}/{userId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingAgent(@RequestBody BookingAgent bookingAgent, @PathVariable Integer bookingId, @PathVariable Integer userId) {
        logger.info(bookingAgent.toString(), "booking agent to update");
        logger.info(bookingId.toString(), "booking id to update");
        logger.info(userId.toString(), "user id to update");
        BookingAgentId bookingAgentId = new BookingAgentId(bookingId, userId);
        BookingAgent toUpdate = bookingAgentService.getBookingAgentById(bookingAgentId);

        if(toUpdate == null) {
            logger.info("booking agent does not exist");
            return new ResponseEntity<>("booking agent does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking agent to change from does exist");
            BookingAgent updated = bookingAgentService.saveBookingAgent(bookingAgent);

        }
    }
*/

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgents/", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgents(@RequestBody List<BookingAgent> bookingAgents) { bookingAgentService.deleteBookingAgents(bookingAgents); }

    @Timed("delete.bookingAgents")
    @RequestMapping(path = "utopia/airlines/bookingAgent/{id}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingAgent(@RequestBody BookingAgent bookingAgent) { bookingAgentService.deleteBookingAgent(bookingAgent); }
}
