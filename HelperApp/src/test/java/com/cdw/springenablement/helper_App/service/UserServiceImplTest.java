package com.cdw.springenablement.helper_App.service;

import com.cdw.springenablement.helper_App.client.models.BookingTechnicianDto;
import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.client.models.TimeSlotDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private HelperRepository helperRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("test123");
        userDto.setGender("MALE");
        List<String> roles = Collections.singletonList("Role_Resident");
        userDto.setRole(roles);

        Users savedUser = new Users();
        savedUser.setId(1);

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(rolesRepository.findByName("Role_Resident")).thenReturn(new Roles());
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        int userId = userService.registerUser(userDto);

        assertEquals(1, userId);
    }

    @Test(expected = Exception.class)
    public void testRegisterUser_UserExists() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        Users existingUser = new Users();
        existingUser.setEmail(userDto.getEmail());
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(existingUser));
        userService.registerUser(userDto);
    }

    @Test(expected = Exception.class)
    public void testRegisterAdminUser() throws Exception {
        UserDto adminUserDto = new UserDto();
        adminUserDto.setEmail("admin@example.com");
        adminUserDto.setRole(Collections.singletonList("Role_Admin"));
        userService.registerUser(adminUserDto);
    }


    @Test
    public void testUpdateHelperSpecialization_EmptySpecialization() throws Exception {
        Users users=new Users();
        when(userRepository.findById(1L)).thenReturn(Optional.of(users));
        List<String> roles = Collections.singletonList("Role_Helper");
        String specialisation="";
        users.setRoles(Collections.singleton(new Roles("Role_Helper")));
        Exception exception = assertThrows(Exception.class, () -> {
            userService.updateHelperSpecialization(1,specialisation);
        });

        assertEquals("Specialization is required for helpers.", exception.getMessage());


    }

    @Test
    public void testUpdateHelperSpecialization_Success() throws Exception {
        Users users=new Users();
        when(userRepository.findById(1L)).thenReturn(Optional.of(users));
        String specialisation="Plumber";
        Helper helper=new Helper();
        helper.setUser(users);
        helper.setSpecialization(specialisation);
        users.setRoles(Collections.singleton(new Roles("Role_Helper")));
        userService.updateHelperSpecialization(1,specialisation);
    }




    @Test
    public void testBookTechnician_NonResidentBook() {
        BookingTechnicianDto bookingTechnicianDto = new BookingTechnicianDto();
        bookingTechnicianDto.setTimeSlotId(4);
        bookingTechnicianDto.setHelperId(1);
        bookingTechnicianDto.setUserId(1);
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(4L);
        Users user = new Users();
        user.setId(1);
        user.setRoles(Collections.singleton(new Roles("Role_Helper")));
        Helper helper=new Helper();
        helper.setUser(user);
        when(timeSlotRepository.findById(4L)).thenReturn(Optional.of(timeSlot));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Exception exception = assertThrows(Exception.class, () -> {
            userService.bookTechnician(bookingTechnicianDto);
        });

        assertEquals("Cannot book slot for non-resident", exception.getMessage());
    }

    @Test
    public void testBookTechnician_Success() throws Exception {
        BookingTechnicianDto bookingTechnicianDto = new BookingTechnicianDto();
        bookingTechnicianDto.setTimeSlotId(4);
        bookingTechnicianDto.setHelperId(1);
        bookingTechnicianDto.setUserId(1);
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(4L);

        Set<Helper> helpers = new HashSet<>();
        Helper helper = new Helper();
        helper.setId(1L);
        helpers.add(helper);

        when(helperRepository.findById(1L)).thenReturn(Optional.of(helper));
        Users user = new Users();
        user.setId(1);
        user.setRoles(Collections.singleton(new Roles("Role_Resident")));
        helper.setUser(user);

        when(timeSlotRepository.findById(4L)).thenReturn(Optional.of(timeSlot));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Bookings bookings = new Bookings();
        bookings.setUsers(user);
        when(bookingRepository.save(any(Bookings.class))).thenReturn(bookings);
        userService.bookTechnician(bookingTechnicianDto);
    }

    @Test
    public  void testGetAppointment(){
        Helper helper=new Helper();
        Users users=new Users();
        users.setId(1);
        users.setFirstName("pavi");
        users.setEmail("pavi@gmail.com");
        helper.setUser(users);
        when(helperRepository.findById(1L)).thenReturn(Optional.of(helper));
        List<HelperAppointmentDto> appointmentDto=new ArrayList<>();
        List<Bookings> bookings=new ArrayList<>();
        HelperAppointmentDto dto=new HelperAppointmentDto();
        Bookings bookingss=new Bookings();
        bookingss.setId(1L);
        //Timeslot
        TimeSlot timeSlot=new TimeSlot();
        timeSlot.setId(1L);
        timeSlot.setStartTime(LocalTime.parse("12:00"));
        timeSlot.setEndTime(LocalTime.parse("13:00"));
        bookingss.setTimeSlot(timeSlot);
        //users
        bookingss.setUsers(users);
        dto.setAppointmentId(Math.toIntExact(bookingss.getId()));
        dto.setStartTime(String.valueOf(bookingss.getTimeSlot().getStartTime()));
        dto.setEndTime(String.valueOf(bookingss.getTimeSlot().getEndTime()));
        dto.setCustomerName(bookingss.getUsers().getFirstName());
        dto.setCustomerEmail(bookingss.getUsers().getEmail());
        appointmentDto.add(dto);
        int helperID=1;
        List<HelperAppointmentDto> appointmentDtos=userService.getAppointment((long) helperID);
        appointmentDtos.add(dto);
        assertEquals(appointmentDto,appointmentDtos);
    }


    @Test
    public void testGetAvailableTechnicians() throws Exception {

        List<Bookings> bookings = new ArrayList<>();
        when(bookingRepository.findAll()).thenReturn(bookings);
        List<TimeSlot> timeSlots = new ArrayList<>();
        when(timeSlotRepository.findAll()).thenReturn(timeSlots);
        List<Helper> availableHelpers = new ArrayList<>();
        when(helperRepository.findAll()).thenReturn(availableHelpers);
        List<TimeSlotDto> timeSlotDtos = userService.getAvailableTechnicians();
        assertEquals(0, timeSlotDtos.size());
    }

    @Test
    public void testGetAvailableTechniciansNoData() throws Exception {
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());
        when(timeSlotRepository.findAll()).thenReturn(new ArrayList<>());
        when(helperRepository.findAll()).thenReturn(new ArrayList<>());
        List<TimeSlotDto> timeSlotDtos = userService.getAvailableTechnicians();
        assertEquals(0, timeSlotDtos.size());
    }







}
