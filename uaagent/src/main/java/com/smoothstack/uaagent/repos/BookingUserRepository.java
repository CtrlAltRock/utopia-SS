package com.smoothstack.uaagent.repos;

import com.smoothstack.uaagent.models.BookingUser;
import com.smoothstack.uaagent.models.BookingUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingUserRepository extends JpaRepository<BookingUser, BookingUserId> {

}
