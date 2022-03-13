package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Passenger;
import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
}
