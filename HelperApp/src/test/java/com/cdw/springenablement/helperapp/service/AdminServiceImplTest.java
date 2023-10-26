package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.ApprovalDto;
import com.cdw.springenablement.helperapp.client.models.ApproveRequestRequest;
import com.cdw.springenablement.helperapp.client.models.HelperDto;
import com.cdw.springenablement.helperapp.client.models.RejectRequestRequest;
import com.cdw.springenablement.helperapp.constants.ErrorConstants;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.AdminServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Class that implements the test for admin
 */
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
        Users user1 = new Users();
        user1.setId(1L);
        user1.setEmail("user1@gmail.com");
        user1.setGender("Female");
        user1.setFirstName("pavi");
        user1.setLastName("g");
        user1.setDateOfBirth(LocalDate.parse("2002-09-01"));

        Users user2 = new Users();
        user2.setId(2L);
        user2.setEmail("user2@gmaile.com");
        user2.setGender("Female");
        user2.setFirstName("Sand");
        user2.setLastName("g");
        user2.setDateOfBirth(LocalDate.parse("2009-05-15"));

        List<Users> mockUsers = new ArrayList<>();
        mockUsers.add(user1);
        mockUsers.add(user2);

        when(userRepository.findByApproved(SuceessConstants.STATUS_REGISTERED, Sort.by(Sort.Order.desc("email"))))
                .thenReturn(mockUsers);
        List<ApprovalDto> approvalDtos = adminService.getApprovalRequest();
        assertEquals(2, approvalDtos.size());
    }



    @Test
    public void testApprovalRequest() {
        Long userId=1L;
        ApproveRequestRequest requestRequest=new ApproveRequestRequest();
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
    public void testApproveRequestNoUsers() {
        ApproveRequestRequest approveRequestRequest = new ApproveRequestRequest();
        approveRequestRequest.setApprovalIds(Arrays.asList(1L, 2L));

        when(userRepository.findByIdInAndApproved(approveRequestRequest.getApprovalIds(), SuceessConstants.STATUS_REGISTERED))
                .thenReturn(Arrays.asList());
        HelperAppException exception = assertThrows(HelperAppException.class,
                () -> adminService.approveRequest(approveRequestRequest));
        assertEquals(SuceessConstants.NO_USER_APPROVE_MESSAGE, exception.getMessage());
    }

    @Test
    public void testRejectRequestNoUsers() {
        RejectRequestRequest rejectRequestRequest = new RejectRequestRequest();
        rejectRequestRequest.setRejectedIds(Arrays.asList(1L, 2L));

        when(userRepository.findByIdInAndApproved(rejectRequestRequest.getRejectedIds(), SuceessConstants.STATUS_REGISTERED))
                .thenReturn(Arrays.asList());
        HelperAppException exception = assertThrows(HelperAppException.class,
                () -> adminService.rejectRequest(rejectRequestRequest));
        assertEquals(SuceessConstants.NO_USER_REJECT_MESSAGE, exception.getMessage());
    }

    @Test
    public void testNoApprovalRequest() {
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


    @Test(expected = Exception.class)
    public  void testRemoveHelpers(){
       Helper helper=new Helper();
       Long id=1L;
       helper.setId(id);
       when(helperRepository.findById(id)).thenReturn(Optional.of(helper));
       Users users=new Users();
       users.setId(id);
       helper.setUser(users);
       Set<Roles> roles=new HashSet<>();
       Roles roleResident = new Roles();
       roleResident.setName(SuceessConstants.ROLE_RESIDENT);
       users.setRoles(roles);
       when(userRepository.findById(id)).thenReturn(Optional.of(users));
       Bookings bookings=new Bookings();
       bookings.setHelperId(id);
       bookings.setUsers(users);
        adminService.removeHelper(id);

    }


    @Test
    public  void testRemoveHelperSuccess(){
        Users users=new Users();
        Long id=1L;
        users.setId(id);
        users.setEmail("example@gmail.com");
        users.setFirstName("pavi");
        Set<Roles> roles=new HashSet<>();
        Roles roleResident = new Roles();
        roleResident.setName(SuceessConstants.ROLE_HELPER);
        users.setRoles(roles);
        Helper helper=new Helper();
        helper.setUser(users);
    }

    @Test
    public void testRemoveHelperHelperNotFound() {
        Long helperId = 1L;
        when(helperRepository.findById(helperId)).thenReturn(Optional.empty());
        HelperAppException exception = assertThrows(HelperAppException.class, () -> adminService.removeHelper(helperId));
        assertEquals(ErrorConstants.HELPER_NOT_FOUND_ERROR, exception.getMessage());
    }







}
