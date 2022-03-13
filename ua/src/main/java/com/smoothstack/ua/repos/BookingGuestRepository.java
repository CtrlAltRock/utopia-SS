package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.BookingGuest;
import com.smoothstack.ua.models.BookingGuestId;
import org.springframework.data.repository.CrudRepository;

public interface BookingGuestRepository extends CrudRepository<BookingGuest, BookingGuestId> {
}
