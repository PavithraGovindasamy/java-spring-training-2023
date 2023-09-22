package com.sirius.springenablement.ticket_booking.repository;

import com.sirius.springenablement.ticket_booking.entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {
   Locations findByName(String locationName);

   Locations getLocationByName(String location);
}