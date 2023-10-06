package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import java.util.List;

/**
 * Interface which has methods of AdminService
 */
public interface AdminService {
    List<ApprovalDto> getApprovalRequest();

    void addNewMember(UserDto userDto) ;

    void removeResident(Integer residentId) ;

    void removeHelper(Integer helperId) ;

    void updateMember(HelperDto helperDto);

    void approveRequest();
}
