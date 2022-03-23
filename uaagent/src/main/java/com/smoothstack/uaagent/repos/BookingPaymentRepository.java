package com.smoothstack.uaagent.repos;


import com.smoothstack.uaagent.models.BookingPayment;
import com.smoothstack.uaagent.models.BookingPaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPaymentRepository extends JpaRepository<BookingPayment, BookingPaymentId> {

}
