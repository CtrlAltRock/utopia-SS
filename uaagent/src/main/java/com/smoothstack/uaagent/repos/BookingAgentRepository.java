package com.smoothstack.uaagent.repos;


import com.smoothstack.uaagent.models.BookingAgent;
import com.smoothstack.uaagent.models.BookingAgentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingAgentRepository extends JpaRepository<BookingAgent, BookingAgentId> {

}
