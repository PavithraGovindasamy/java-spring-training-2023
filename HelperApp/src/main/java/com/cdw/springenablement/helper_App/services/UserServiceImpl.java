package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.dto.TimeSlotDtoDisplay;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private HelperRepository helperRepository;

    public int registerUser(UserDto userDto) throws Exception {


        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {

                throw new Exception("User with this email already exists.");
        }

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setApproved("registered");

        Set<Roles> userRoles = new HashSet<>();

        List<String> roleNames = userDto.getRole();
        for (String roleName : roleNames) {
            if (roleName.equals("Role_Admin")) {
                throw new Exception("Cannot register Admin");
            }
            Roles role = rolesRepository.findByName(roleName);
            if (role == null) {
                throw new Exception("Role not found");
            }
            System.out.println("roles" + role);

            userRoles.add(role);

        }
        user.setRoles(userRoles);


        Users savedUser = userRepository.save(user);

        return savedUser.getId();


    }

        public void bookTechnician(BookingTechnicianDto bookingTechnicianDto) throws Exception {
        Long timeSlotId = Long.valueOf(bookingTechnicianDto.getTimeSlotId());
        Long helperId = Long.valueOf(bookingTechnicianDto.getHelperId());

        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new Exception("TimeSlot not found"));
        Helper helper = helperRepository.findById(helperId).orElse(null);

        Bookings booking = bookingRepository.findByHelperAndTimeSlot(helper, timeSlot);
            Users users = userRepository.findById(Long.valueOf(bookingTechnicianDto.getUserId())).orElse(null);
            Set<Roles> roles=users.getRoles();
            for(Roles role:roles){
                if(role.getName().equals("Role_Helper")){
                    throw  new Exception("Cannot book slot for non-resident");
                }
            }
        if (timeSlot.getHelpers().contains(helper)) {
            throw new Exception("Selected helper is already booked for the specified time slot");
        }


        timeSlot.getHelpers().add(helper);
        timeSlotRepository.save(timeSlot);

        Bookings newBooking = new Bookings();

        newBooking.setUsers(users);
        newBooking.setHelper(helper);
        newBooking.setTimeSlot(timeSlot);
        bookingRepository.save(newBooking);
    }




    @Override
    public List<HelperAppointmentDto> getAppointment(Long helperId) {
        Helper helper = helperRepository.findById(helperId).orElse(null);
        List<HelperAppointmentDto> appointmentDtos = new ArrayList<>();
        System.out.println("hj" + helper);
        if (helper != null) {
            List<Bookings> bookings = bookingRepository.findByHelper(helper);
            System.out.println("hj" + bookings);

            for (Bookings booking : bookings) {
                HelperAppointmentDto dto = new HelperAppointmentDto();

                dto.setAppointmentId(Math.toIntExact(booking.getId()));
                dto.setStartTime(String.valueOf(booking.getTimeSlot().getStartTime()));
                dto.setEndTime(String.valueOf(booking.getTimeSlot().getEndTime()));
                dto.setCustomerName(booking.getUsers().getFirstName() + " " + booking.getUsers().getLastName());
                dto.setCustomerEmail(booking.getUsers().getEmail());
                System.out.println("hj" + dto);
                appointmentDtos.add(dto);
            }
        }

        return appointmentDtos;
    }


    @Override
    public List<TimeSlotDto> getAvailableTechnicians(LocalDate date, String profession, String startTime, String endTime) throws Exception {
        List<Bookings> bookings = bookingRepository.findAll();

        List<TimeSlot> availableSlots = timeSlotRepository.findAll();

        List<TimeSlotDto> availableSlotsDtoList = new ArrayList<>();

        for (TimeSlot slot : availableSlots) {
            List<Helper> availableHelpers = helperRepository.findByTimeSlotsNotContains(slot);
            TimeSlotDto timeSlotDto = TimeSlotDtoDisplay.createTimeSlotDto(slot.getId(), slot.getStartTime(), slot.getEndTime(), slot.getDate(), availableHelpers);
            availableSlotsDtoList.add(timeSlotDto);
        }


        System.out.println("Available TimeSlots: " + availableSlotsDtoList);

        return availableSlotsDtoList;
    }



    public void updateHelperSpecialization(int userId, String specialization) throws Exception {
        Users user = userRepository.findById((long) userId).orElse(null);

        if (user == null) {
            throw new Exception("User not found with ID: " + userId);
        }

        Set<Roles> roles = user.getRoles();
        boolean isHelper = roles.stream().anyMatch(role -> role.getName().equals("Role_Helper"));

        if (isHelper) {
            if (specialization != null && !specialization.trim().isEmpty()) {
                Helper helper = new Helper();
                helper.setUser(user);
                helper.setSpecialization(specialization);

                helperRepository.save(helper);
            } else {
                throw new Exception("Specialization is required for helpers.");
            }
        }
    }


}






