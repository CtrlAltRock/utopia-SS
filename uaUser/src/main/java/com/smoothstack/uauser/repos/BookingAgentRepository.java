package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.BookingAgent;
import com.smoothstack.uauser.models.BookingAgentId;
import org.springframework.data.repository.CrudRepository;

public interface BookingAgentRepository extends CrudRepository<BookingAgent, BookingAgentId> {

}
