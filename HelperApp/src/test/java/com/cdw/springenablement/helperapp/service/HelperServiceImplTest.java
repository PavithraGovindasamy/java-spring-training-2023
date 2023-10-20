package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDtos;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.BookingRepository;
import com.cdw.springenablement.helperapp.repository.HelperRepository;
import com.cdw.springenablement.helperapp.repository.TimeSlotRepository;
import com.cdw.springenablement.helperapp.repository.UserRepository;
import com.cdw.springenablement.helperapp.services.HelperServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class HelperServiceImplTest {

    @InjectMocks
    private HelperServiceImpl helperService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HelperRepository helperRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = Exception.class)
    public  void testGetAppointment(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("example",null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        long userId=1L;
        Long helperId=1L;
        Users user = new Users();
        user.setId(userId);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        user.setRoles(Collections.singleton(new Roles(SuceessConstants.ROLE_RESIDENT)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Set<Roles> roles=user.getRoles();
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Helper helper=new Helper();
        Users users=new Users();
        users.setId(userId);
        users.setFirstName("pavi");
        users.setEmail("pavi@gmail.com");
        helper.setUser(users);
        helper.setId(helperId);

        when(helperRepository.findById(helperId)).thenReturn(Optional.of(helper));
        when(helperRepository.findByUserId(userId)).thenReturn(helper);
        Long id=helper.getId();
        List<Bookings> bookings=new ArrayList<>();

        List<HelperAppointmentDto> appointmentDto=new ArrayList<>();
        appointmentDto=bookings.stream()
                .map(bookings1 -> {
                    HelperAppointmentDto dto=new HelperAppointmentDto();
                    dto.setAppointmentId(bookings1.getId());
                    dto.setStartTime(String.valueOf(bookings1.getTimeSlot().getStartTime()));
                    dto.setEndTime(String.valueOf(bookings1.getTimeSlot().getEndTime()));
                    dto.setCustomerName(bookings1.getUsers().getFirstName());
                    dto.setCustomerEmail(bookings1.getUsers().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
        Bookings bookingss=new Bookings();
        long bookingId=2L;
        bookingss.setId(bookingId);
        TimeSlot timeSlot=new TimeSlot();
        long timeSlotId=1L;
        timeSlot.setId(timeSlotId);
        timeSlot.setStartTime(LocalTime.parse("12:00"));
        timeSlot.setEndTime(LocalTime.parse("13:00"));
        List<Bookings> bookings1 = bookingRepository.findByHelperId(timeSlotId);
        bookingss.setTimeSlot(timeSlot);
        bookingss.setUsers(users);
        bookings1.add(bookingss);

        List<HelperAppointmentDto> appointmentDtos=helperService.getAppointment();
        assertEquals(appointmentDto,appointmentDtos);
    }

    @Test
    public  void testGetAppointment_Success(){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("example",null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        long userId=1L;
        Long helperId=1L;
        Users user = new Users();
        user.setId(userId);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));
        user.setRoles(Collections.singleton(new Roles(SuceessConstants.ROLE_RESIDENT)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Set<Roles> roles=user.getRoles();
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Helper helper=new Helper();

        Users users=new Users();
        users.setId(userId);
        users.setFirstName("pavi");
        users.setEmail("pavi@gmail.com");
        helper.setUser(users);
        helper.setId(helperId);

        when(helperRepository.findById(helperId)).thenReturn(Optional.of(helper));
        when(helperRepository.findByUserId(userId)).thenReturn(helper);
        Long id=helper.getId();
        List<Bookings> bookings=new ArrayList<>();
        when(bookingRepository.findByHelperId(id)).thenReturn(bookings);
        List<HelperAppointmentDto> appointmentDto=new ArrayList<>();
        appointmentDto=bookings.stream()
                .map(bookings1 -> {
                    HelperAppointmentDto dto=new HelperAppointmentDto();
                    dto.setAppointmentId(bookings1.getId());
                    dto.setStartTime(String.valueOf(bookings1.getTimeSlot().getStartTime()));
                    dto.setEndTime(String.valueOf(bookings1.getTimeSlot().getEndTime()));
                    dto.setCustomerName(bookings1.getUsers().getFirstName());
                    dto.setCustomerEmail(bookings1.getUsers().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
        Bookings bookingss=new Bookings();
        long bookingId=2L;
        bookingss.setId(bookingId);
        TimeSlot timeSlot=new TimeSlot();
        long timeSlotId=1L;
        timeSlot.setId(timeSlotId);
        timeSlot.setStartTime(LocalTime.parse("12:00"));
        timeSlot.setEndTime(LocalTime.parse("13:00"));
        List<Bookings> bookings1 = bookingRepository.findByHelperId(timeSlotId);
        bookingss.setTimeSlot(timeSlot);
        bookingss.setUsers(users);
        bookings1.add(bookingss);
        List<HelperAppointmentDto> appointmentDtos=helperService.getAppointment();
    }




    @Test(expected = HelperAppException.class)
    public void testGetAvailableTechnicians_Failure() {
        Long id = 1L;
        List<Bookings> bookings = new ArrayList<>();
        Bookings booking = new Bookings();
        booking.setHelperId(id);
        bookings.add(booking);
        //mock data
        LocalDate date = LocalDate.parse("2023-09-09");
        boolean flag = false;
        List<TimeSlot> timeSlots = new ArrayList<>();
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(id);
        timeSlots.add(timeSlot);
        List<TimeSlot> timeSlotDtos = new ArrayList<>();
        if (id != null) {
            TimeSlot timeSlot1 = new TimeSlot();
            List<Helper> helpers = helperService.getAvailableHelpersForTimeSlot(date, timeSlot, flag);
            timeSlotDtos.add(timeSlot1);
        }

    }

    @Test(expected = HelperAppException.class)
    public void testGetAvailableTechnicians() {
        LocalDate currentDate = LocalDate.parse("2024-09-09");
        Long timeslotId = 1L;
        TimeSlot mockTimeSlot = new TimeSlot();
        mockTimeSlot.setId(timeslotId);
        mockTimeSlot.setStartTime(LocalTime.parse("09:00"));
        mockTimeSlot.setEndTime(LocalTime.parse("10:00"));
        Helper mockHelper = new Helper();
        mockHelper.setId(1L);
        mockHelper.setSpecialization("Plumber");
        List<Helper> availableHelpers = List.of(mockHelper);
        when(timeSlotRepository.findById(timeslotId)).thenReturn(Optional.of(mockTimeSlot));
        List<TimeSlotDto> result = helperService.getAvailableTechnicians(currentDate, timeslotId);
    }

    @Test(expected = DateTimeException.class)
    public void testGetAvailableTechniciansWithNoAvailableHelpers() {
        List<Bookings> bookings = new ArrayList<>();
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalDate date = LocalDate.of(1, 2, 2002);
        Long id = 1L;
        helperService.getAvailableTechnicians(date, id);
    }


    @Test
    public void testAvailableHelpers() {
        List<Bookings> bookings = new ArrayList<>();
        Long id = 1L;
        LocalDate date = LocalDate.parse("2023-09-09");

    }


    @Test
    public void testAllTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        TimeSlot timeSlot = new TimeSlot();
        long id = 1L;
        timeSlot.setId(id);
        timeSlot.setStartTime(LocalTime.parse("01:00"));
        timeSlot.setEndTime(LocalTime.parse("02:00"));
        timeSlots.add(timeSlot);
        when(timeSlotRepository.findAll()).thenReturn(timeSlots);
        List<TimeSlotDtos> timeSlotDtos = new ArrayList<>();
        for (TimeSlot timeSlot1 : timeSlots) {
            TimeSlotDtos timeSlotDtos1 = new TimeSlotDtos();
            timeSlotDtos1.setEndTime(String.valueOf(timeSlot1.getEndTime()));
            timeSlotDtos1.setStartTime(String.valueOf(timeSlot1.getStartTime()));
            timeSlotDtos1.setId(id);
            timeSlotDtos.add(timeSlotDtos1);
        }
        List<TimeSlotDtos> timeSlotDtos2 = helperService.getAllTimeSlots();
    }

    @Test
    public void testGetAvailableHelpers() {
        List<Bookings> bookings = new ArrayList<>();
        Long id = 1L;
        LocalDate date = LocalDate.parse("2023-09-09");
        Users users = new Users();
        Boolean flag = true;
        users.setId(id);
        users.setEmail("pavi@gmail.com");
        Bookings book = new Bookings();
        book.setUsers(users);
        book.setHelperId(id);
        book.setDate(date);
        List<Long> bookedHelperIds = bookings.stream()
                .map(booking -> Long.valueOf(booking.getHelperId()))
                .collect(Collectors.toList());
        List<Helper> helpers = new ArrayList<>();
        if (bookedHelperIds.isEmpty()) {
            when(helperRepository.findAll()).thenReturn(helpers);
        } else {
            when(helperRepository.findByIdNotIn(bookedHelperIds)).thenReturn(helpers);
        }
        TimeSlot timeSlot = new TimeSlot();

        List<Helper> availableHelper = helperService.getAvailableHelpersForTimeSlot(date, timeSlot, flag);
        assertEquals(availableHelper, helpers);
    }




    @Test
    public void testGetAvailableTechniciansWithNullTimeslotId() {
        LocalDate currentDate = LocalDate.now();
        Long timeslotId = null;
        LocalDate date = LocalDate.of(2023, 12, 12);
        List<TimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(new TimeSlot());
        List<Helper> availableHelpers = new ArrayList<>();
        availableHelpers.add(new Helper());
        when(timeSlotRepository.findAll()).thenReturn(timeSlots);
        List<TimeSlotDto> result = helperService.getAvailableTechnicians(date, timeslotId);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAvailableTechnicians_InvalidTimeslotId() {
        LocalDate date = LocalDate.now().plusDays(1);
        Long timeslotId = 999L;
        HelperAppException exception = assertThrows(HelperAppException.class,
                () -> helperService.getAvailableTechnicians(date, timeslotId));

        assertEquals("Invalid timeslotId provided", exception.getMessage());
    }



}
