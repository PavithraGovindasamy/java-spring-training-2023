package com.cdw.springenablement.helperapp.controller;


import com.cdw.springenablement.helperapp.client.api.HelpersApi;
import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.services.interfaces.HelperService;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
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


}
