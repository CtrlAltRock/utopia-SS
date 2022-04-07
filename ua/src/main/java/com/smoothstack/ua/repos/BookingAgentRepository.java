package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.BookingAgent;
import com.smoothstack.ua.models.BookingAgentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingAgentRepository extends JpaRepository<BookingAgent, BookingAgentId> {

}
