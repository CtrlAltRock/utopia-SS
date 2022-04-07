package com.smoothstack.ua.services;

import com.smoothstack.ua.models.BookingPayment;
import com.smoothstack.ua.models.BookingPaymentId;
import com.smoothstack.ua.repos.BookingPaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingPaymentService {

    Logger logger = LoggerFactory.getLogger(BookingPaymentService.class);

    @Autowired
    private BookingPaymentRepository bookingPaymentRepository;

    public List<BookingPayment> getAllBookingPayments() {
        return (List<BookingPayment>) bookingPaymentRepository.findAll();
    }

    public BookingPayment getBookingPaymentById(Integer bookingId) {
        Optional<BookingPayment> bookingPayment = bookingPaymentRepository.findById(new BookingPaymentId(bookingId));
        if(bookingPayment.isPresent()) {
            return bookingPayment.get();
        }
        else return null;
    }

    public BookingPayment saveBookingPayment(BookingPayment bookingPayment) {
        BookingPayment posted = bookingPaymentRepository.save(bookingPayment);
        return posted;
    }

    public void deleteBookingPayment(BookingPayment bookingPayment) {
        bookingPaymentRepository.delete(bookingPayment);
    }

    public void deleteBookingPaymentById(BookingPaymentId bookingPaymentId) {
        bookingPaymentRepository.deleteById(bookingPaymentId);
    }
}
