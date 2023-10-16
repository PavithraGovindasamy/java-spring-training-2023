package com.cdw.springenablement.helperapp.service;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.HelperServiceImpl;
import com.cdw.springenablement.helperapp.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.*;

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
    @Test(expected = HelperAppException.class)
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
        List<HelperAppointmentDto> appointmentDto=new ArrayList<>();
        List<Bookings> bookings=new ArrayList<>();
        HelperAppointmentDto dto=new HelperAppointmentDto();
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
        dto.setAppointmentId(bookingss.getId());
        dto.setStartTime(String.valueOf(bookingss.getTimeSlot().getStartTime()));
        dto.setEndTime(String.valueOf(bookingss.getTimeSlot().getEndTime()));
        dto.setCustomerName(bookingss.getUsers().getFirstName());
        dto.setCustomerEmail(bookingss.getUsers().getEmail());
        appointmentDto.add(dto);
        List<HelperAppointmentDto> appointmentDtos=helperService.getAppointment();
        appointmentDtos.add(dto);
        assertEquals(appointmentDto,appointmentDtos);
    }

}
