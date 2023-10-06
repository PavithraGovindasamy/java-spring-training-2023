package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.dto.TimeSlotDtoDisplay;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.exception.HelperAppException;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service class providing user-related functionalities.
 * @Author pavithra
 */
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

    /**
     * Registers a new user in the system.
     *
     * @param userDto
     * @return ID of the registered user.
     *
     */
    public int registerUser(UserDto userDto)  {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new HelperAppException("User with this email already exists.");
        }
        List<String> roleNames = userDto.getRole();
        Set<Roles> userRoles=new HashSet<>();
        for (String roleName : roleNames) {
            if (!roleName.equals("Role_Admin")) {
                Roles role = rolesRepository.findByName(roleName);
                if (role == null) {
                    throw new HelperAppException("Invalid role: " + roleName);
                }
                userRoles.add(role);
            } else {
                throw new HelperAppException("Cannot register Admin");
            }
        }

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setApproved("registered");
        user.setRoles(userRoles);

        Users savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    /**
     * Books a technician for a specified time slot.
     *
     * @param bookingTechnicianDto BookingTechnicianDto object containing booking details.
     *
     */

        public void bookTechnician(BookingTechnicianDto bookingTechnicianDto) {
        Long timeSlotId = Long.valueOf(bookingTechnicianDto.getTimeSlotId());
        Long helperId = Long.valueOf(bookingTechnicianDto.getHelperId());
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
        Bookings booking = bookingRepository.findByHelperIdAndTimeSlot(helperId, timeSlot);
            Users users = userRepository.findById(Long.valueOf(bookingTechnicianDto.getUserId())).orElse(null);
            Set<Roles> roles=users.getRoles();
            boolean isRoleHelper = roles.stream()
                    .anyMatch(role -> role.getName().equals("Role_Helper"));
            if (isRoleHelper) {
                throw new HelperAppException("Cannot book slot for non-resident");
            }
            Helper helper = helperRepository.findById(helperId).orElse(null);
            int userId=helper.getUser().getId();
            Users helperUser=userRepository.findById((long) userId).orElse(null);
            Set<Roles> helperRole=helperUser.getRoles();
            if(helperUser==null){
                throw new HelperAppException("No such helper exists");
            }
                if (booking!=null) {
            throw new HelperAppException("Selected helper is already booked for the specified time slot");
        }
        timeSlotRepository.save(timeSlot);
        Bookings newBooking = new Bookings();
        newBooking.setUsers(users);
        newBooking.setHelperId(Math.toIntExact(helperId));
        newBooking.setTimeSlot(timeSlot);
        bookingRepository.save(newBooking);
    }



    /**
     * Retrieves a list of appointments for a specified helper.
     *
     * @param helperId ID of the helper.
     *
     */

    @Override
    public List<HelperAppointmentDto> getAppointment(Long helperId) {
        Helper helper = helperRepository.findById(helperId).orElse(null);
        int userId = helper.getUser().getId();
        Users users = userRepository.findById((long) userId).orElse(null);
        List<HelperAppointmentDto> appointmentDtos = null;
        if (helper != null) {
            List<Bookings> bookings = bookingRepository.findByHelperId(helperId);
            appointmentDtos = bookings.stream()
                    .map(booking -> {
                        HelperAppointmentDto dto = new HelperAppointmentDto();
                        dto.setAppointmentId(Math.toIntExact(booking.getId()));
                        dto.setStartTime(String.valueOf(booking.getTimeSlot().getStartTime()));
                        dto.setEndTime(String.valueOf(booking.getTimeSlot().getEndTime()));
                        dto.setCustomerName(booking.getUsers().getFirstName() + " " + booking.getUsers().getLastName());
                        dto.setCustomerEmail(booking.getUsers().getEmail());
                        return dto;
                    })
                    .collect(Collectors.toList());

        }
        return appointmentDtos;
    }

    /**
     * Retrieves available technicians for a user
     *
     */

    @Override
    public List<TimeSlotDto> getAvailableTechnicians() {
        List<Bookings> bookings = bookingRepository.findAll();
        List<TimeSlot> allTimeSlots = timeSlotRepository.findAll();
        List<TimeSlotDto> availableSlotsDtoList = allTimeSlots.stream()
                .map(slot -> {
                    List<Bookings> bookingsForSlot = bookingRepository.findByTimeSlotId(slot.getId());
                    List<Long> bookedHelperIds = bookingsForSlot.stream()
                            .map(booking -> Long.valueOf(booking.getHelperId()))
                            .collect(Collectors.toList());

                    List<Helper> availableHelpers = bookedHelperIds.isEmpty() ?
                            helperRepository.findAll() :
                            helperRepository.findByIdNotIn(bookedHelperIds);

                    return TimeSlotDtoDisplay.createTimeSlotDto(slot.getId(), slot.getStartTime(), slot.getEndTime(), slot.getDate(), availableHelpers);
                })
                .collect(Collectors.toList());
        return availableSlotsDtoList;
    }


    /**
     * Updates the specialization of a helper.
     *
     * @param userId
     * @param specialization
     */

    public void updateHelperSpecialization(int userId, String specialization)  {
        Users user = userRepository.findById((long) userId).orElse(null);
        if (user == null) {
            throw new HelperAppException("User not found with ID: " + userId);
        }
        Set<Roles> roles = user.getRoles();

        boolean isHelper = roles.stream()
                .filter(Objects::nonNull)
                .anyMatch(role -> "Role_Helper".equals(role.getName()));

        if (isHelper) {
            if (specialization != null && !specialization.trim().isEmpty()) {
                Helper helper = new Helper();
                helper.setUser(user);
                helper.setSpecialization(specialization);
                helperRepository.save(helper);
            } else {
                throw new HelperAppException("Specialization is required for helpers.");
            }
        }
    }


}






