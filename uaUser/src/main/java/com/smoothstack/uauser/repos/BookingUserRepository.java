package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.BookingUser;
import com.smoothstack.uauser.models.BookingUserId;
import org.springframework.data.repository.CrudRepository;

public interface BookingUserRepository extends CrudRepository<BookingUser, BookingUserId> {

}
