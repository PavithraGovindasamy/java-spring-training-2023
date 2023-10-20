package com.cdw.springenablement.helperapp.controller;


import com.cdw.springenablement.helperapp.client.api.HelpersApi;
import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDtos;
import com.cdw.springenablement.helperapp.services.interfaces.HelperService;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller class that handles helper endpoints.
 * @Author pavithra
 */
@RestController
public class HelperController implements HelpersApi  {

  @Autowired
  private HelperService helperService;

  /**
   *
   * @return the list of helper appointments
   */

  @Override
  public ResponseEntity<List<HelperAppointmentDto>> getHelperAppointments() {
    List<HelperAppointmentDto> helperAppointmentDtos=helperService.getAppointment();
    return ResponseEntity.ok(helperAppointmentDtos);
  }

  /**
   *
   * Method which give the available technicians timeslot
   * @return response where list of timeslot is returned
   */


  @Override
  public ResponseEntity<List<TimeSlotDto>> getAvailableTechnicians(LocalDate date, Long timeSlotId) {
    List<TimeSlotDto> technicianDtos = helperService.getAvailableTechnicians(date,timeSlotId);
    return ResponseEntity.ok(technicianDtos);
  }

  /**
   * Returns all the timeslots
   * @return
   */

  @Override
  public ResponseEntity<List<TimeSlotDtos>> getAllTimeSlots() {
    List<TimeSlotDtos> timeSlotDtos=helperService.getAllTimeSlots();
    return  ResponseEntity.ok(timeSlotDtos);
  }



}
