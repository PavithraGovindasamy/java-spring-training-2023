package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.ErrorConstants;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.exception.HelperAppException;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * class that handles the admin side endpoints
 * @Author pavithra
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl  implements AdminService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private HelperRepository helperRepository;



    /**
     *
     * @return
     * method that returns the approval requests
     */
    @Override
    public List<ApprovalDto> getApprovalRequest() {

        List<Users> usersToApprove = userRepository.findByApproved(SuceessConstants.STATUS_REGISTERED);

        List<ApprovalDto> approvalDtos = usersToApprove.stream()
                .map(user -> {
                    ApprovalDto approvalDto = new ApprovalDto();
                    approvalDto.setUserId(Math.toIntExact(user.getId()));
                    approvalDto.setEmail(user.getEmail());
                    approvalDto.setGender(user.getGender());
                    approvalDto.setFirstName(user.getFirstName());
                    approvalDto.setLastName(user.getLastName());
                    approvalDto.setDateOfBirth(user.getDateOfBirth());
                    return approvalDto;
                })
                .collect(Collectors.toList());

        return approvalDtos;
    }


    /**
     * Method thqt gets the approved requests
     */
    public void approveRequest(ApproveRequestRequest userApproveDto) {
        System.out.println("userAPPROVE" + userApproveDto);
        List<Long> approvedIds = userApproveDto.getApprovalIds();

        List<Users> usersToApprove = userRepository.findByIdInAndApproved(approvedIds, SuceessConstants.STATUS_REGISTERED);

        if (usersToApprove.isEmpty()) {
            throw new HelperAppException(SuceessConstants.NO_USER_APPROVE_MESSAGE);

        }
        usersToApprove.forEach(user -> user.setApproved(SuceessConstants.STATUS_APPROVED));


        userRepository.saveAll(usersToApprove);
    }

    @Override
    public void rejectRequest(RejectRequestRequest rejectRequestRequest) {
        List<Long> rejectedIds = rejectRequestRequest.getRejectedIds();
        List<Users> usersToReject = userRepository.findByIdInAndApproved(rejectedIds, SuceessConstants.STATUS_REGISTERED);
        if (usersToReject.isEmpty()) {
        throw new HelperAppException(SuceessConstants.NO_USER_REJECT_MESSAGE);
        }
        usersToReject.forEach(user -> user.setApproved(SuceessConstants.STATUS_REJECTED));
        userRepository.saveAll(usersToReject);

    }


    /**
     *
     * @param userDto
     * Method that adds the member
     */
    @Override
    public void addNewMember(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            try {
                String message= ErrorConstants.USER_ALREADY_EXISTS_ERROR;
                throw new HelperAppException(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setApproved(SuceessConstants.STATUS_APPROVED);
        List<String> roleNames = userDto.getRole();
        Set<Roles> userRoles = roleNames.stream()
                .map(roleName -> rolesRepository.findByName(roleName))
                .collect(Collectors.toSet());
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    /**
     *
     * @param residentId
     * Method that remove the resident
     */
    @Override
    public void removeResident(Long residentId)  {
        Users user = userRepository.findById(residentId).orElse(null);
        if (user != null) {
            Set<Roles> roles = user.getRoles();
            for (Bookings booking : user.getBookings()) {
                Long timeslotId=booking.getTimeSlot().getId();
                TimeSlot timeSlot=timeSlotRepository.findById(timeslotId).orElse(null);
                bookingRepository.delete(booking);
            }
            boolean isResident = roles.stream().anyMatch(role -> role.getName().equals("Role_Resident"));
            if (isResident) {
                userRepository.deleteById(Long.valueOf(residentId));
            } else {
                String message= ErrorConstants.DELETE_NON_RESIDENT_ERROR;
                throw  new HelperAppException(message);
            }
        } else {
            String message= ErrorConstants.USER_NOT_FOUND_ERROR;
           throw  new HelperAppException(message);
        }
    }

    /**
     *
     * @param helperId
     * Method that remove the helper
     */
    @Override
    public void removeHelper(Long helperId)  {
        Helper helper = helperRepository.findById(helperId).orElse(null);
        if (helper != null) {
            String message= ErrorConstants.HELPER_NOT_FOUND_ERROR;
            throw new HelperAppException(message);
        }
        Long userId = helper.getUser().getId();
        Users user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
        List<Bookings> bookings=bookingRepository.findByHelperId(Long.valueOf(helperId));
        bookings.forEach(book -> bookingRepository.deleteById(book.getId()));
        helperRepository.delete(helper);
    }

    /**
     *
     * @param helperDto
     * method that updates the details of user
     */

    @Override
    public void updateMember(HelperDto helperDto) {
        Users users=userRepository.findById(Long.valueOf(helperDto.getUserId())).orElse(null);
        users.setEmail(helperDto.getEmail());
        users.setGender(helperDto.getGender());
        users.setDateOfBirth(helperDto.getDateOfBirth());
        users.setFirstName(helperDto.getFirstName());
        users.setLastName(helperDto.getLastName());
        userRepository.save(users);
    }


}
