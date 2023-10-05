package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.entity.Users;

import java.util.List;

public interface AdminService {
    List<ApprovalDto> getApprovalRequest();

    void addNewMember(UserDto userDto) throws Exception;

    void removeResident(Integer residentId) throws Exception;

    void removeHelper(Integer helperId) throws Exception;

    void updateMember(HelperDto helperDto);

    void approveRequest();
}
