package com.cdw.springenablement.helper_App.controller;


import com.cdw.springenablement.helper_App.client.api.HelpersApi;
import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
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
  private UserService userService;

  /**
   *
   * @param helperId  (required)
   * @return the list of helper appointments
   */

  @Override
  public ResponseEntity<List<HelperAppointmentDto>> getHelperAppointments(Long helperId) {
    List<HelperAppointmentDto> helperAppointmentDtos=userService.getAppointment(helperId);
    return ResponseEntity.ok(helperAppointmentDtos);
  }


}
