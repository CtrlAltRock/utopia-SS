package com.smoothstack.ua.services;

import com.smoothstack.ua.models.BookingGuest;
import com.smoothstack.ua.repos.BookingGuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingGuestService {

    Logger logger = LoggerFactory.getLogger(BookingGuestService.class);

    @Autowired
    private BookingGuestRepository bookingGuestRepository;

    public List<BookingGuest> getAllBookingGuests() { return (List<BookingGuest>) bookingGuestRepository.findAll(); }

    public void saveBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.saveAll(bookingGuests); }

    public void saveBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.save(bookingGuest);}

    public void deleteBookingGuests(List<BookingGuest> bookingGuests) { bookingGuestRepository.deleteAll(bookingGuests); }

    public void deleteBookingGuest(BookingGuest bookingGuest) { bookingGuestRepository.delete(bookingGuest); }
}
