package com.sirius.springenablement.ticket_booking.dto;
import java.time.LocalDate;
import com.sirius.springenablement.ticket_booking.entity.Locations;
@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class ShowRequestDto {
    private LocalDate date;
    private String timeSlot;
    private String movieName;



}