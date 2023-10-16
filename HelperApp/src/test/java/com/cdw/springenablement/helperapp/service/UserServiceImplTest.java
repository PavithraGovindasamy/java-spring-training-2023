package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.ErrorConstants;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.lang.model.type.ErrorType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("example",null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Users user=new Users();
        user.setEmail("test");
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("test123");
        userDto.setGender("MALE");
        List<String> roles = Collections.singletonList("Role_Resident");
        userDto.setRole(roles);

        Users savedUser = new Users();
        savedUser.setId(1L);

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(rolesRepository.findByName("Role_Resident")).thenReturn(new Roles());
        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        Long userId = userService.registerUser(userDto);

        assertEquals(1, userId);
    }

    @Test(expected = Exception.class)
    public void testRegisterUser_UserExists() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        Users existingUser = new Users();
        existingUser.setEmail(userDto.getEmail());
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
        Long id=1L;
        users.setRoles(Collections.singleton(new Roles("Role_Helper")));
        Exception exception = assertThrows(Exception.class, () -> {
            userService.updateHelperSpecialization(id,specialisation);
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
        Long userId=1L;
        helper.setSpecialization(specialisation);
        users.setRoles(Collections.singleton(new Roles("Role_Helper")));
        userService.updateHelperSpecialization(userId,specialisation);
    }


    @Test(expected = HelperAppException.class)
    public void testGetAvailableTechnicians_NoBookings() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());
        when(timeSlotRepository.findAll()).thenReturn(Arrays.asList(new TimeSlot()));
        userService.getAvailableTechnicians();
    }





    @Test
    public void testBookTechnician_Success() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("example",null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        long userId=1L;
        Users user = new Users();
        user.setId(userId);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        user.setRoles(Collections.singleton(new Roles(SuceessConstants.ROLE_RESIDENT)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Set<Roles> roles=user.getRoles();
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        BookingTechnicianDto bookingTechnicianDto = new BookingTechnicianDto();
        long timeSlotID=4L;
        long helperId=1L;
        bookingTechnicianDto.setTimeSlotId(timeSlotID);
        bookingTechnicianDto.setHelperId(helperId);
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotID);
        Set<Helper> helpers = new HashSet<>();
        Helper helper = new Helper();
        helper.setId(helperId);
        helpers.add(helper);
        when(helperRepository.findById(helperId)).thenReturn(Optional.of(helper));
        helper.setUser(user);
        when(timeSlotRepository.findById(timeSlotID)).thenReturn(Optional.of(timeSlot));
        Bookings bookings = new Bookings();
        bookings.setUsers(user);
        when(bookingRepository.save(any(Bookings.class))).thenReturn(bookings);
        userService.bookTechnician(bookingTechnicianDto);
    }


    @Test
    public void testGetAvailableTechnicians(){
        Long id=1L;
        List<Bookings> bookings = new ArrayList<>();
        Bookings booking = new Bookings();
        booking.setHelperId(id);
        bookings.add(booking);

        List<TimeSlot> timeSlots = new ArrayList<>();
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(id);
        timeSlots.add(timeSlot);

    }



    @Test(expected = HelperAppException.class)
    public void testGetAvailableTechniciansWithNoAvailableHelpers() {
        List<Bookings> bookings = new ArrayList<>();
        List<TimeSlot> timeSlots = new ArrayList<>();
        when(bookingRepository.findAll()).thenReturn(bookings);
        when(timeSlotRepository.findAll()).thenReturn(timeSlots);
        userService.getAvailableTechnicians();
    }


    @Test(expected = HelperAppException.class)
    public void testGetUserBookings() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken("example", null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userId = 1L;
        Long id = 1L;
        Users user = new Users();
        user.setId(userId);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        Long userIid=user.getId();
        user.setRoles(Collections.singleton(new Roles(SuceessConstants.ROLE_RESIDENT)));
        user.setEmail("test@example.com");
        user.setGender("Male");
        userRepository.save(user);
        Helper helper = new Helper();
        helper.setSpecialization("Specialization");
        helperRepository.save(helper);
        Bookings booking = new Bookings();
        booking.setUsers(user);
        booking.setHelperId(helper.getId());
        bookingRepository.save(booking);
        List<Bookings> bookings=new ArrayList<>();
        bookings.add(booking);
        List<BookingDto> result = userService.getUserBookings();
        List<BookingDto> expectedBookings = new ArrayList<>();
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(id);
        bookingDto.setHelperId(id);
        bookingDto.setSpecialisation("Specialization");
        BookingDtoTimeslotDetails timeSlotDetails = new BookingDtoTimeslotDetails();
        timeSlotDetails.setEndTime("12:00");
        timeSlotDetails.setStartTime("11:00");
        bookingDto.setTimeslotDetails(timeSlotDetails);
        expectedBookings.add(bookingDto);

    }

    @Test(expected = HelperAppException.class)
    public void testGetUserBookings_NoBookingsFound() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Users user = new Users();
        user.setId(1L);
        Optional<Users> optionalUser = Optional.of(user);

        when(authentication.getName()).thenReturn("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(optionalUser);

        userService.getUserBookings();
    }


    @Test(expected = NoSuchElementException.class)
    public void testGetUserBookings_NoUserAuthenticated() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.getUserBookings();
    }



}











