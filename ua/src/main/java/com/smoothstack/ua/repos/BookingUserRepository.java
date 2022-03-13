package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.models.BookingUser;
import com.smoothstack.ua.models.BookingUserId;
import org.springframework.data.repository.CrudRepository;

public interface BookingUserRepository extends CrudRepository<BookingUser, BookingUserId> {

}
