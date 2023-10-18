package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.BookingRepository;
import com.cdw.springenablement.helperapp.repository.HelperRepository;
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

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
