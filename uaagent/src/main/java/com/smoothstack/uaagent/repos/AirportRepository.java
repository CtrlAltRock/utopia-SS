package com.smoothstack.uaagent.repos;

import com.smoothstack.uaagent.models.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
