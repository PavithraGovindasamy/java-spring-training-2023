package com.cdw.springenablement.helper_App.service;

import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.AdminServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.parameters.P;
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
        Users user = new Users();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setGender("Male");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setApproved("registered");

        when(userRepository.findByApproved("registered")).thenReturn(Collections.singletonList(user));

        List<ApprovalDto> approvalDtos = adminService.getApprovalRequest();

        assertEquals(1, approvalDtos.size());
        ApprovalDto approvalDto = new ApprovalDto();
        approvalDto.setGender(user.getGender());
        approvalDto.setUserId(user.getId());
        approvalDto.setLastName(user.getLastName());
        approvalDto.setEmail(user.getEmail());
        approvalDto.setFirstName(user.getFirstName());
        approvalDto.setDateOfBirth(user.getDateOfBirth());

        List<ApprovalDto> expectedList = Collections.singletonList(approvalDto);
        assertEquals(expectedList, approvalDtos);
    }
    @Test
    public void testUpdateMember(){
       Users users=new Users();
       int userId=1;
       when(userRepository.findById(1L)).thenReturn(Optional.of(users));
        HelperDto dto=new HelperDto();
        dto.setEmail("pavi@gmail.com");
        dto.setLastName("g");
        dto.setFirstName("pavi");
        dto.setGender("Female");
        dto.setUserId(userId);
        dto.setDateOfBirth(LocalDate.parse("2023-09-09"));
        Users savedUser = new Users();
        savedUser.setId(1);
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);
        adminService.updateMember(dto);
    }

    @Test
    public  void testApproveRequest(){
        List<Users> usersToApprove=new ArrayList<>();
        when(userRepository.findByApproved("registered")).thenReturn(usersToApprove);
        adminService.approveRequest();

    }


    @Test
    public void testAddNewMember_Success() throws Exception {

        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setGender("Gender");
        userDto.setFirstName("test");
        userDto.setLastName("g");
        userDto.setDateOfBirth(LocalDate.parse("2023-09-02"));
        userDto.setRole(Arrays.asList("plumber"));

        Roles role = new Roles();
        role.setName("plumber");

        Users savedUser = new Users();
        savedUser.setId(1);

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(rolesRepository.findByName("plumber")).thenReturn(role);
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        adminService.addNewMember(userDto);


    }

    @Test(expected = Exception.class)
    public void testAddMember_UserExists() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        Users existingUser = new Users();
        existingUser.setEmail(userDto.getEmail());

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));
        adminService.addNewMember(userDto);
    }


    @Test
    public void testRemoveResident() throws Exception {
        int residentId=1;
        Users users=new Users();
        when(userRepository.findById((long) residentId)).thenReturn(Optional.of(users));
        Set<Roles> roles = new HashSet<>();

        Roles roleResident = new Roles();
        roleResident.setName("Role_Resident");

        roles.add(roleResident);
        users.setRoles(roles);

        users.setRoles(roles);
        List<Bookings> bookings=new ArrayList<>();
        users.setBookings(bookings);
        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setBooked(false);
        doNothing().when(userRepository).deleteById((long) residentId);
        adminService.removeResident(1);

    }


    @Test
    public  void testRemoveHelper() throws Exception {
        int helperId = 1;
        int userId = 1;
        Helper helper = new Helper();
        helper.setId((long) helperId);
        Users users=new Users();
        users.setId(2);
        helper.setUser(users);
        Users user = new Users();
        user.setId(userId);
        Set<TimeSlot> timeSlots=new HashSet<>();
        List<Bookings> bookings = new ArrayList<>();
        when(helperRepository.findById((long) helperId)).thenReturn(Optional.of(helper));
        adminService.removeHelper(Math.toIntExact(helperId));

    }


}
