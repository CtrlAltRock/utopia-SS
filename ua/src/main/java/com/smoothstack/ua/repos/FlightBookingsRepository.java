package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.FlightBookings;
import com.smoothstack.ua.models.FlightBookingsId;
import org.springframework.data.repository.CrudRepository;

public interface FlightBookingsRepository extends CrudRepository<FlightBookings, FlightBookingsId> {
}
