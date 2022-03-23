package com.smoothstack.uaagent.repos;

import com.smoothstack.uaagent.models.BookingGuest;
import com.smoothstack.uaagent.models.BookingGuestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingGuestRepository extends JpaRepository<BookingGuest, BookingGuestId> {
}
