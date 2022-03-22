package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.BookingUser;
import com.smoothstack.uauser.models.BookingUserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingUserRepository extends CrudRepository<BookingUser, BookingUserId> {

    @Query(value = "Select * from booking_user where user_id = :id", nativeQuery = true)
    List<BookingUser> findAllByUserId(@Param("id") Long id);
}
