package com.sirius.springenablement.ticket_booking.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long> {

    

    java.util.List<com.sirius.springenablement.ticket_booking.entity.Shows> findByDate(LocalDate bookingDate);

}