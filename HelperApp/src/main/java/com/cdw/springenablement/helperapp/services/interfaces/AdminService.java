package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.*;

import java.util.List;

/**
 * Interface which has methods of AdminService
 */
public interface AdminService {
    List<ApprovalDto> getApprovalRequest();
    void removeResident(Long residentId) ;
    void removeHelper(Long helperId) ;
    void updateMember(HelperDto helperDto);
    void approveRequest(ApproveRequestRequest userApproveDto);
    void rejectRequest(RejectRequestRequest rejectRequestRequest);

}
