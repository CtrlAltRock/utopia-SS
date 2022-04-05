package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.BookingGuest;
import com.smoothstack.ua.services.BookingGuestService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class BookingGuestController {

    private Logger logger = LoggerFactory.getLogger(BookingGuestController.class);

    @Autowired
    private BookingGuestService bookingGuestService;

    @Timed("get.bookingGuests.dump")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public List<BookingGuest> getBookingGuests() { return bookingGuestService.getAllBookingGuests(); }

    @Timed("post.bookingGuests")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public void saveBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { bookingGuestService.saveBookingGuests(bookingGuests); }

    @Timed("post.bookingGuest")
    @RequestMapping(path = "utopia/airlines/bookingGuest/{id}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public void saveBookingGuest(@RequestBody BookingGuest bookingGuest) { bookingGuestService.saveBookingGuest(bookingGuest); }

    @Timed("delete.bookingGuests")
    @RequestMapping(path = "utopia/airlines/bookingGuests/", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuests(@RequestBody List<BookingGuest> bookingGuests) { bookingGuestService.deleteBookingGuests(bookingGuests); }

    @Timed("delete.bookingGuest")
    @RequestMapping(path = "utopia/airlines/bookingGuest/{id}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public void deleteBookingGuest(@RequestBody BookingGuest bookingGuest) { bookingGuestService.deleteBookingGuest(bookingGuest); }

}
