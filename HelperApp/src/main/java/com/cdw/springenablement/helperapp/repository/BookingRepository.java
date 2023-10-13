package com.cdw.springenablement.helperapp.repository;

import com.cdw.springenablement.helperapp.entity.Bookings;
import com.cdw.springenablement.helperapp.entity.TimeSlot;
import com.cdw.springenablement.helperapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository which handles booking related operation
 */
public interface BookingRepository extends JpaRepository<Bookings, Long> {
    List<Bookings> findByUsers(Users users);


    Bookings findByHelperIdAndTimeSlot(Long helperId, TimeSlot timeSlot);


    List<Bookings> findByHelperId(Long helperId);

    List<Bookings> findByTimeSlotId(Long id);

    List<Bookings> findByUsers(Optional<Users> newUser);
}
