package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.ErrorConstants;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
public class UserServiceImpl implements UserService {
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

    String registered= SuceessConstants.STATUS_REGISTERED;

    String approved= SuceessConstants.STATUS_APPROVED;

    /**
     * Registers a new user in the system.
     *
     * @param userDto
     * @return ID of the registered user.
     */
    public Long registerUser(UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
        boolean isRoleAdmin = false;
        if (newUser.isPresent()) {
            Set<Roles> roles = newUser.get().getRoles();
            isRoleAdmin = roles.stream()
                    .anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_ADMIN));
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new HelperAppException(ErrorConstants.USER_ALREADY_EXISTS_ERROR);
        }
        List<String> roleNames = userDto.getRole();
        Set<Roles> userRoles = new HashSet<>();
        for (String roleName : roleNames) {
            if (!roleName.equals(SuceessConstants.ROLE_ADMIN)) {
                Roles role = rolesRepository.findByName(roleName);
                if (role == null) {
                    throw new HelperAppException(ErrorConstants.INVALID_ROLE_ERROR + roleName);
                }
                userRoles.add(role);
            } else {
                throw new HelperAppException(ErrorConstants.CANNOT_REGISTER_ADMIN_ERROR);
            }
        }

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (isRoleAdmin) {
            user.setApproved(approved);
        } else {
            user.setApproved(registered);
        }
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
            Long userid=newUser.get().getId();

            Long timeSlotId =bookingTechnicianDto.getTimeSlotId();
            Long helperId = bookingTechnicianDto.getHelperId();
            TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
             Bookings booking = bookingRepository.findByHelperIdAndTimeSlot(helperId, timeSlot);
            Users users = userRepository.findById(userid).orElse(null);
            Set<Roles> roles=users.getRoles();
            boolean isRoleHelper = roles.stream()
                    .anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_HELPER));
            if (isRoleHelper) {
                throw new HelperAppException(ErrorConstants.CANNOT_BOOK_NON_RESIDENT_ERROR);
            }
            Helper helper = helperRepository.findById(helperId).orElse(null);
            Long userId=helper.getUser().getId();
            Users helperUser=userRepository.findById((long) userId).orElse(null);
            Set<Roles> helperRole=helperUser.getRoles();
            if(helperUser==null){
                throw new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR);
            }
                if (booking!=null) {
            throw new HelperAppException(ErrorConstants.HELPER_ALREADY_BOOKED_ERROR);
        }
        timeSlotRepository.save(timeSlot);
        Bookings newBooking = new Bookings();
        newBooking.setUsers(users);
        newBooking.setHelperId(helperId);
        newBooking.setTimeSlot(timeSlot);
        bookingRepository.save(newBooking);
    }



    /**
     * Retrieves a list of appointments for a specified helper.
     *
     *
     */

    @Override
    public List<HelperAppointmentDto> getAppointment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
        Long helperId=newUser.get().getId();
        Helper helpers=helperRepository.findByUserId(helperId);
        System.out.println("HELPERID"+helpers.getId());
        Helper helper = helperRepository.findById(helpers.getId()).orElse(null);
        System.out.println("s,m");
        Long userId = helper.getUser().getId();
        Users users = userRepository.findById((long) userId).orElse(null);
        List<HelperAppointmentDto> appointmentDtos = null;
        if (helper != null) {
            List<Bookings> bookings = bookingRepository.findByHelperId(helpers.getId());
            appointmentDtos = bookings.stream()
                    .map(booking -> {
                        HelperAppointmentDto dto = new HelperAppointmentDto();
                        dto.setAppointmentId(booking.getId());
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

    public void updateHelperSpecialization(Long userId, String specialization)  {
        Users user = userRepository.findById((long) userId).orElse(null);
        if (user == null) {
            throw new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR_FORMAT + userId);
        }
        Set<Roles> roles = user.getRoles();

        boolean isHelper = roles.stream()
                .filter(Objects::nonNull)
                .anyMatch(role -> SuceessConstants.ROLE_HELPER.equals(role.getName()));

        if (isHelper) {
            if (specialization != null && !specialization.trim().isEmpty()) {
                Helper helper = new Helper();
                helper.setUser(user);
                helper.setSpecialization(specialization);
                helperRepository.save(helper);
            } else {
                throw new HelperAppException(ErrorConstants.SPECIALIZATION_REQUIRED_ERROR);
            }
        }
    }

}






