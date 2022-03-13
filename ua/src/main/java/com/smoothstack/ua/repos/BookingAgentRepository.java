package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Booking;
import com.smoothstack.ua.models.BookingAgent;
import com.smoothstack.ua.models.BookingAgentId;
import com.smoothstack.ua.models.BookingPayment;
import org.springframework.data.repository.CrudRepository;

public interface BookingAgentRepository extends CrudRepository<BookingAgent, BookingAgentId> {

}
