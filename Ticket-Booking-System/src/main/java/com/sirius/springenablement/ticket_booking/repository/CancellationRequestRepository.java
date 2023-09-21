package com.sirius.springenablement.ticket_booking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sirius.springenablement.ticket_booking.entity.CancellationRequest;

@org.springframework.stereotype.Repository
public interface CancellationRequestRepository extends JpaRepository<CancellationRequest, Long> {



}