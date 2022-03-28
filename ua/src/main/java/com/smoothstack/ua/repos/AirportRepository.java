package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, String> {
}
