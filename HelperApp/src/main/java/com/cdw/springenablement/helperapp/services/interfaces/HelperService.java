package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;

import java.util.List;

public interface HelperService {

    List<HelperAppointmentDto> getAppointment();
}
