package com.sirius.springenablement.ticket_booking.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import  java.util.List;

@Repository
public interface ShowsRepository extends JpaRepository<Shows, Long> {

    

   List<Shows> findByDate(LocalDate bookingDate);



    Shows findByMovieName(String movieName);

    List<Shows> findByDateAndTimeSlot(LocalDate bookingDate, String timeSlot);

    Shows findByMovieNameAndTheatreName(String movieName,String theatreName);

    List<Shows> findByDateAndMovieNameAndTimeSlot(LocalDate bookingDate, String movieName, String timeSlot);

    List<Shows> findByDateAndMovieName(LocalDate bookingDate, String movieName);
}