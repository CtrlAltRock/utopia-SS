package com.smoothstack.ua.controllers;

import com.smoothstack.ua.models.BookingPayment;
import com.smoothstack.ua.models.BookingPaymentId;
import com.smoothstack.ua.services.BookingPaymentService;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class BookingPaymentController {

    private Logger logger = LoggerFactory.getLogger(BookingPaymentController.class);

    @Autowired
    private BookingPaymentService bookingPaymentService;

    @Timed("get.bookingPayments.dump")
    @RequestMapping(path = "utopia/airlines/bookingPayments/", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingPayments() {
        return new ResponseEntity<>(bookingPaymentService.getAllBookingPayments(), HttpStatus.OK);
    }

    @Timed("get.bookingPayments.id")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingId}", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getBookingPaymentsById(@PathVariable Integer bookingId) {
        BookingPaymentId bookingPaymentId = new BookingPaymentId(bookingId);
        BookingPayment bookingPayment  = bookingPaymentService.getBookingPaymentById(bookingId);
        if(bookingPayment == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else{
            logger.info("booking payment id does exist");
            return new ResponseEntity<>(bookingPayment, HttpStatus.OK);
        }
    }
    @Timed("post.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> postBookingPayments(@RequestBody BookingPayment bookingPayment) {
        BookingPayment check = bookingPaymentService.getBookingPaymentById(bookingPayment.getBookingPaymentId().getBooking_id());
        if(check == null) {
            logger.info("booking payment id doesn't exist");
            BookingPayment posted = bookingPaymentService.saveBookingPayment(bookingPayment);
            return new ResponseEntity<>(posted, HttpStatus.OK);

        }
        else{
            logger.info("booking payment id already exists");
            return new ResponseEntity<>("booking payment id already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @Timed("put.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingPaymentId}", method = RequestMethod.PUT, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> putBookingPayments(@RequestBody BookingPayment bookingPayment, @PathVariable Integer bookingPaymentId) {
        BookingPayment check = bookingPaymentService.getBookingPaymentById(bookingPaymentId);

        if(check == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking payment id does exist");
            BookingPaymentId putBookingPaymentId = new BookingPaymentId(bookingPaymentId);
            bookingPayment.setBookingPaymentId(putBookingPaymentId);
            BookingPayment put = bookingPaymentService.saveBookingPayment(bookingPayment);
            return new ResponseEntity<>(put, HttpStatus.OK);
        }
    }


    @Timed("delete.bookingPayments")
    @RequestMapping(path = "utopia/airlines/bookingPayments/{bookingPaymentId}", method = RequestMethod.DELETE, consumes = {"application/json", "application/xml"})
    public ResponseEntity<?> deleteBookingPayments(@PathVariable Integer bookingPaymentId) {
        BookingPayment check = bookingPaymentService.getBookingPaymentById(bookingPaymentId);

        if(check == null) {
            logger.info("booking payment id does not exist");
            return new ResponseEntity<>("booking payment id does not exist", HttpStatus.BAD_REQUEST);
        }
        else {
            logger.info("booking payment id does exist");
            bookingPaymentService.deleteBookingPaymentById(new BookingPaymentId(bookingPaymentId));
            return new ResponseEntity<>("deleting " + check.toString(), HttpStatus.OK);
        }
    }
}
