package com.cdw.springenablement.helper_App.repository;

import com.cdw.springenablement.helper_App.entity.Bookings;
import com.cdw.springenablement.helper_App.entity.TimeSlot;
import com.cdw.springenablement.helper_App.entity.Users;
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
