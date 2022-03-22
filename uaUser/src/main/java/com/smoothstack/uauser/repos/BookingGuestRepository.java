package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.BookingGuest;
import com.smoothstack.uauser.models.BookingGuestId;
import org.springframework.data.repository.CrudRepository;

public interface BookingGuestRepository extends CrudRepository<BookingGuest, BookingGuestId> {
}
