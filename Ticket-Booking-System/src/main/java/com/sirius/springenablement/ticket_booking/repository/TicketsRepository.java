package com.sirius.springenablement.ticket_booking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sirius.springenablement.ticket_booking.entity.Tickets;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Long> {

    List<Tickets> findByShowId(int id);
}