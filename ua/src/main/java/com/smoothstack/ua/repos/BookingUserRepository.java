package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.BookingUser;
import com.smoothstack.ua.models.BookingUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingUserRepository extends JpaRepository<BookingUser, BookingUserId> {

}
