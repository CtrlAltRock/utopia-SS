package com.smoothstack.uauser.repos;

import com.smoothstack.uauser.models.BookingPayment;
import com.smoothstack.uauser.models.BookingPaymentId;
import org.springframework.data.repository.CrudRepository;

public interface BookingPaymentRepository extends CrudRepository<BookingPayment, BookingPaymentId> {

}
