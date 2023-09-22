package com.sirius.springenablement.ticket_booking.repository;

import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {


    List<Bookings> findByUserId(Long userId);

   Optional<Bookings> findByUserIdAndShowId(Long userId, Long userId1);
}