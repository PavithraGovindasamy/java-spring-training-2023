package com.cdw.springenablement.helperapp.services;

import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.ErrorConstants;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Service class providing user-related functionalities.
 *
 * @Author pavithra
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private HelperRepository helperRepository;

    String registered = SuceessConstants.STATUS_REGISTERED;

    String approved = SuceessConstants.STATUS_APPROVED;

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
     */

    public void bookTechnician(BookingTechnicianDto bookingTechnicianDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
        Long userid = newUser.get().getId();
        Long timeSlotId = bookingTechnicianDto.getTimeSlotId();
        Long helperId = bookingTechnicianDto.getHelperId();
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
        Bookings booking = bookingRepository.findByHelperIdAndTimeSlotAndDate(helperId, timeSlot,bookingTechnicianDto.getDate());
        Users users = userRepository.findById(userid).orElse(null);
        Set<Roles> roles = users.getRoles();
        boolean isRoleHelper = roles.stream()
                .anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_HELPER));
        if (isRoleHelper) {
            throw new HelperAppException(ErrorConstants.CANNOT_BOOK_NON_RESIDENT_ERROR);
        }
        Helper helper = helperRepository.findById(helperId).orElse(null);
        if (helper == null) {
            throw new HelperAppException(ErrorConstants.NO_HELPER_EXISTS_ERROR);
        }
        Long userId = helper.getUser().getId();
        Users helperUser = userRepository.findById((long) userId).orElse(null);

        Set<Roles> helperRole = helperUser.getRoles();

        if(timeSlot==null){
            throw  new HelperAppException(ErrorConstants.NO_TIMESLOT);
        }
        if (booking != null) {
            throw new HelperAppException(ErrorConstants.HELPER_ALREADY_BOOKED_ERROR);
        }
        timeSlotRepository.save(timeSlot);
        Bookings newBooking = new Bookings();
        newBooking.setUsers(users);
        newBooking.setHelperId(helperId);
        newBooking.setTimeSlot(timeSlot);
        newBooking.setDate(bookingTechnicianDto.getDate());
        newBooking.setActive(true);
        bookingRepository.save(newBooking);
    }








    /**
     * Updates the specialization of a helper.
     *
     * @param userId
     * @param specialization
     */

    public void updateHelperSpecialization(Long userId, String specialization) {
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

    /**
     * Returning the user booking details with appropraite helpers and timeslot info
     *
     * @return
     */

    @Override
    public List<BookingDto> getUserBookings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
        Long id = newUser.get().getId();
        List<Bookings> bookingDtos = bookingRepository.findByUsers(newUser);

        List<BookingDto> booking = bookingDtos.stream()
                .map(bookings -> {
                    BookingDto bookingDto = new BookingDto();
                    Optional<Helper> helper = helperRepository.findById(bookings.getHelperId());
                    if (helper.isPresent()) {
                        bookingDto.isHelperAvailable("true");
                        bookingDto.setBookingId(bookings.getId());
                        bookingDto.setHelperId(bookings.getHelperId());
                        TimeSlot timeSlot = bookings.getTimeSlot();
                        BookingDtoTimeslotDetails timeslots = new BookingDtoTimeslotDetails();
                        timeslots.setStartTime(timeSlot.getStartTime().toString());
                        timeslots.setEndTime(timeSlot.getEndTime().toString());
                        timeslots.setDate(bookings.getDate());
                        bookingDto.setTimeslotDetails(timeslots);
                    } else {
                        bookingDto.isHelperAvailable("Helper not available");
                        bookingDto.setBookingId(bookings.getId());
                    }
                    return bookingDto;
                })
                .collect(Collectors.toList());

        return booking;
    }


    }






