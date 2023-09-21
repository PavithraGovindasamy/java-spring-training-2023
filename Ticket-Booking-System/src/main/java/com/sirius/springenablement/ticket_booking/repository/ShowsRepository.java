package com.sirius.springenablement.ticket_booking.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import  java.util.List;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long> {

    

    java.util.List<com.sirius.springenablement.ticket_booking.entity.Shows> findByDate(LocalDate bookingDate);



    com.sirius.springenablement.ticket_booking.entity.Shows findByMovieName(String movieName);

    List<Shows> findByDateAndTimeSlot(LocalDate bookingDate, String timeSlot);

    Shows findByMovieNameAndTheatreName(String movieName,String theatreName);

    java.util.List<com.sirius.springenablement.ticket_booking.entity.Shows> findByDateAndMovieNameAndTimeSlot(java.time.LocalDate bookingDate, String movieName, String timeSlot);

    java.util.List<com.sirius.springenablement.ticket_booking.entity.Shows> findByDateAndMovieName(java.time.LocalDate bookingDate, String movieName);
}