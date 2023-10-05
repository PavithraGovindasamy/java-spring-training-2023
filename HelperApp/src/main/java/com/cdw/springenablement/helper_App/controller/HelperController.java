package com.cdw.springenablement.helper_App.controller;


import com.cdw.springenablement.helper_App.client.api.HelpersApi;
import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelperController implements HelpersApi  {

  @Autowired
  private UserService userService;


  @Override
  public ResponseEntity<List<HelperAppointmentDto>> getHelperAppointments(Long helperId) {
    System.out.println("HELPER");
    List<HelperAppointmentDto> helperAppointmentDtos=userService.getAppointment(helperId);
    return ResponseEntity.ok(helperAppointmentDtos);
  }


}
