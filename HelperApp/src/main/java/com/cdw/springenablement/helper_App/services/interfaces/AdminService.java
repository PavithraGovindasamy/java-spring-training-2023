package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.*;

import java.util.List;

/**
 * Interface which has methods of AdminService
 */
public interface AdminService {
    List<ApprovalDto> getApprovalRequest();

    void addNewMember(UserDto userDto) ;

    void removeResident(Long residentId) ;

    void removeHelper(Long helperId) ;

    void updateMember(HelperDto helperDto);

    void approveRequest(ApproveRequestRequest userApproveDto);

    void rejectRequest(RejectRequestRequest rejectRequestRequest);

}
