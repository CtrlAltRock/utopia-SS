package com.smoothstack.ua.services;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.repos.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingService {

    Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    public Booking getBookingsById(Integer bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()) {
            return booking.get();
        }
        else return null;
    }

    public Booking saveBooking(Booking booking) {
        Booking posted = bookingRepository.save(booking);
        return posted;
    }

    public void deleteBookingById(Integer id) {
        bookingRepository.deleteById(id);
    }
}
