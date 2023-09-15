package com.sirius.springenablement.ticket_booking.repository;



@org.springframework.stereotype.Repository
public interface TicketsRepository extends org.springframework.data.jpa.repository.JpaRepository<com.sirius.springenablement.ticket_booking.entity.Tickets, Long> {

    java.util.List<com.sirius.springenablement.ticket_booking.entity.Tickets> findByShowId(int id);
}