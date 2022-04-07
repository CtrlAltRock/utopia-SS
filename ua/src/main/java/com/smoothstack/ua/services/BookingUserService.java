package com.smoothstack.ua.services;

import com.smoothstack.ua.models.BookingUser;
import com.smoothstack.ua.models.BookingUserId;
import com.smoothstack.ua.repos.BookingUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingUserService {

    Logger logger = LoggerFactory.getLogger(BookingUserService.class);

    @Autowired
    BookingUserRepository bookingUserRepository;

    public List<BookingUser> getAllBookingUsers() {
        return (List<BookingUser>) bookingUserRepository.findAll();
    }

    public BookingUser getBookingUserById(BookingUserId bookingUserId) {
        Optional<BookingUser> bookingUser = bookingUserRepository.findById(bookingUserId);
        if(bookingUser.isPresent()) {
            return bookingUser.get();
        }
        else return null;
    }

    public BookingUser saveBookingUser(BookingUser bookingUser) {
        BookingUser posted = bookingUserRepository.save(bookingUser);
        return posted;
    }

    public void deleteBookingUser(BookingUser bookingUser) {
        bookingUserRepository.delete(bookingUser);
    }
}
