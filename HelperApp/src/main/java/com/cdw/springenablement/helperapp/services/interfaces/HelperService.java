package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;

import java.util.List;
/**
 * Interface which has methods of HelperService
 */
public interface HelperService {

    List<HelperAppointmentDto> getAppointment();
}
