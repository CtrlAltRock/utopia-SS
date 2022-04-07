package com.smoothstack.ua.repos;

import com.smoothstack.ua.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
