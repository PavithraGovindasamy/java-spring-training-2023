package com.sirius.springenablement.ticket_booking.repository;

@org.springframework.stereotype.Repository
public interface LocationsRepository extends org.springframework.data.jpa.repository.JpaRepository<com.sirius.springenablement.ticket_booking.entity.Locations, Long> {
    com.sirius.springenablement.ticket_booking.entity.Locations findByName(String locationName);

    com.sirius.springenablement.ticket_booking.entity.Locations getLocationByName(String location);
}