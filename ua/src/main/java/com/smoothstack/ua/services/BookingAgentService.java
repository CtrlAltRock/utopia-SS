package com.smoothstack.ua.services;

import com.smoothstack.ua.models.BookingAgent;
import com.smoothstack.ua.models.BookingAgentId;
import com.smoothstack.ua.repos.BookingAgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingAgentService {

    Logger logger = LoggerFactory.getLogger(BookingAgentService.class);

    @Autowired
    BookingAgentRepository bookingAgentRepository;

    public List<BookingAgent> getAllBookingAgents() { return (List<BookingAgent>) bookingAgentRepository.findAll(); }

    public BookingAgent getBookingAgentById(BookingAgentId bookingAgentId) {
        Optional<BookingAgent> bookingAgent = bookingAgentRepository.findById(bookingAgentId);
        if(bookingAgent.isPresent()) {
            return bookingAgent.get();
        }
        else return null;
    }

    public void saveBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.saveAll(bookingAgents); }

    public BookingAgent saveBookingAgent(BookingAgent bookingAgent) {
        BookingAgent posted = bookingAgentRepository.save(bookingAgent);
        return posted;
    }

    public void deleteBookingAgents(List<BookingAgent> bookingAgents) { bookingAgentRepository.deleteAll(bookingAgents); }

    public void deleteBookingAgent(BookingAgent bookingAgent) { bookingAgentRepository.delete(bookingAgent); }

}
