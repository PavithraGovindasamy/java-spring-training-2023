package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.ApprovalDto;
import com.cdw.springenablement.helperapp.client.models.ApproveRequestRequest;
import com.cdw.springenablement.helperapp.client.models.HelperDto;
import com.cdw.springenablement.helperapp.client.models.RejectRequestRequest;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.AdminServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private HelperRepository helperRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetApprovalRequest() {
        Users user=new Users();
        Long userId=1L;
        user.setId(userId);
        List<Users> users = Arrays.asList(user);
        when(userRepository.findByApproved(SuceessConstants.STATUS_REGISTERED)).thenReturn(users);
        List<ApprovalDto> result = adminService.getApprovalRequest();
    }


    @Test
    public void testApprovalRequest() {
        Long userId=1L;
        ApproveRequestRequest requestRequest=new ApproveRequestRequest();
        requestRequest.setApprovalIds(Collections.singletonList(1L));
        List<Long> approvedIds=requestRequest.getApprovalIds();
        Users users=new Users();
        users.setId(1L);
        users.setEmail("test@gmail.com");
        List<Users> usersList=new ArrayList<>();
        usersList.add(users);
        when(userRepository.findByIdInAndApproved(approvedIds,SuceessConstants.STATUS_REGISTERED)).thenReturn(usersList);
        adminService.approveRequest(requestRequest);
    }


    @Test
    public void testejectRequest() {
        Long userId=1L;
        RejectRequestRequest requestRequest=new RejectRequestRequest();
        requestRequest.setRejectedIds(Collections.singletonList(1L));
        List<Long> approvedIds=requestRequest.getRejectedIds();
        Users users=new Users();
        users.setId(1L);
        users.setEmail("test@gmail.com");
        List<Users> usersList=new ArrayList<>();
        usersList.add(users);
        when(userRepository.findByIdInAndApproved(approvedIds,SuceessConstants.STATUS_REGISTERED)).thenReturn(usersList);
        adminService.rejectRequest(requestRequest);
    }



    @Test
    public void testUpdateMember(){
       Users users=new Users();
       Long userId=1L;
       when(userRepository.findById(userId)).thenReturn(Optional.of(users));
        HelperDto dto=new HelperDto();
        dto.setEmail("pavi@gmail.com");
        dto.setLastName("g");
        dto.setFirstName("pavi");
        dto.setGender("Female");
        dto.setUserId(userId);
        dto.setDateOfBirth(LocalDate.parse("2023-09-09"));
        Users savedUser = new Users();
        savedUser.setId(userId);
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);
        adminService.updateMember(dto);
    }



    @Test
    public void testRemoveResident() throws Exception {
        Long residentId=1L;
        Users users=new Users();
        when(userRepository.findById((long) residentId)).thenReturn(Optional.of(users));
        Set<Roles> roles = new HashSet<>();
        Roles roleResident = new Roles();
        roleResident.setName(SuceessConstants.ROLE_RESIDENT);
        roles.add(roleResident);
        users.setRoles(roles);
        users.setRoles(roles);
        List<Bookings> bookings=new ArrayList<>();
        users.setBookings(bookings);
        TimeSlot timeSlot=new TimeSlot();
        doNothing().when(userRepository).deleteById(residentId);
        Long id=1L;
        adminService.removeResident(id);

    }


    @Test
    public  void testRemoveHelper() throws Exception {
       Helper helper=new Helper();
       Long id=1L;
       helper.setId(id);
       when(helperRepository.findById(id)).thenReturn(Optional.of(helper));
       Users users=new Users();
       users.setId(id);
       helper.setUser(users);
       when(userRepository.findById(id)).thenReturn(Optional.of(users));
       Bookings bookings=new Bookings();
       bookings.setHelperId(id);
       bookings.setUsers(users);
       doNothing().when(userRepository).delete(users);
       doNothing().when(helperRepository).delete(helper);
       adminService.removeHelper(id);

    }


}
