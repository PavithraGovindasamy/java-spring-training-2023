package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDtos;

import java.time.LocalDate;
import java.util.List;
/**
 * Interface which has methods of HelperService
 */
public interface HelperService {

    List<HelperAppointmentDto> getAppointment();

    List<TimeSlotDto> getAvailableTechnicians(LocalDate date, Long timeSlotId) ;

    List<TimeSlotDtos> getAllTimeSlots();


}
