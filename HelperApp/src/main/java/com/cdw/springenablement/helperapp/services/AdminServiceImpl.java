package com.cdw.springenablement.helperapp.services;
import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.ErrorConstants;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.*;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * class that handles the admin side endpoints
 *
 * @Author pavithra
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

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
     * @return method that returns the approval requests
     */
    @Override
    public List<ApprovalDto> getApprovalRequest() {
        Pageable pageable= PageRequest.of(0,3);
        List<Users> usersToApprove = userRepository.findByApproved(
                SuceessConstants.STATUS_REGISTERED,
                Sort.by(Sort.Order.desc("email"))
        );
        List<ApprovalDto> approvalDtos = usersToApprove.stream().map(user -> {
            ApprovalDto approvalDto = new ApprovalDto();
            approvalDto.setUserId(Math.toIntExact(user.getId()));
            approvalDto.setEmail(user.getEmail());
            approvalDto.setGender(user.getGender());
            approvalDto.setFirstName(user.getFirstName());
            approvalDto.setLastName(user.getLastName());
            approvalDto.setDateOfBirth(user.getDateOfBirth());
            return approvalDto;
        }).collect(Collectors.toList());
        return approvalDtos;
    }


    /**
     * Method thqt gets the approved requests
     *
     * @return
     */
    public List<Long> approveRequest(ApproveRequestRequest userApproveDto) {

        List<Long> approvedIds = userApproveDto.getApprovalIds();
        List<Users> usersToApprove = userRepository.findByIdInAndApproved(approvedIds, SuceessConstants.STATUS_REGISTERED);
        if (usersToApprove.isEmpty()) {
            throw new HelperAppException(SuceessConstants.NO_USER_APPROVE_MESSAGE);
        }
        usersToApprove.forEach(user -> user.setApproved(SuceessConstants.STATUS_APPROVED));
        userRepository.saveAll(usersToApprove);
        return approvedIds;
    }

    /**
     * Method thqt gets the approved requests
     * @param rejectRequestRequest
     * @return
     */

    @Override
    public List<Long> rejectRequest(RejectRequestRequest rejectRequestRequest) {
        List<Long> rejectedIds = rejectRequestRequest.getRejectedIds();
        List<Users> usersToReject = userRepository.findByIdInAndApproved(rejectedIds, SuceessConstants.STATUS_REGISTERED);
        if (usersToReject.isEmpty()) {
            throw new HelperAppException(SuceessConstants.NO_USER_REJECT_MESSAGE);
        }
        usersToReject.forEach(user -> user.setApproved(SuceessConstants.STATUS_REJECTED));
        userRepository.saveAll(usersToReject);
        return rejectedIds;
    }


    /**
     * @param residentId Method that remove the resident
     */
    @Override
    public void removeResident(Long residentId) {
        Users user = userRepository.findById(residentId).orElse(null);
        if (user != null) {
            Set<Roles> roles = user.getRoles();
            for (Bookings booking : user.getBookings()) {
                Long timeslotId = booking.getTimeSlot().getId();
                TimeSlot timeSlot = timeSlotRepository.findById(timeslotId).orElse(null);
                bookingRepository.delete(booking);
            }
            boolean isResident = roles.stream().anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_RESIDENT));
            if (isResident) {
                userRepository.deleteById(Long.valueOf(residentId));
            } else {
                throw new HelperAppException(ErrorConstants.DELETE_NON_RESIDENT_ERROR);
            }
        } else {
            throw new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR);
        }
    }

    /**
     * @param helperId Method that remove the helper
     */
    @Override
    public void removeHelper(Long helperId) {
        Helper helper = helperRepository.findById(helperId).orElse(null);
        if (helper != null) {

            Long userId = helper.getUser().getId();
            Users user = userRepository.findById(userId).orElse(null);
            Set<Roles> roles = null;
            if (user != null) {
                roles = user.getRoles();
            }
            boolean isHelper = roles.stream().anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_HELPER));
            if (isHelper) {
                userRepository.delete(user);
            } else {
                throw new HelperAppException(ErrorConstants.DELETE_NON_HELPER_ERROR);
            }
        }
        else {
            throw new HelperAppException(ErrorConstants.HELPER_NOT_FOUND_ERROR);
        }

        List<Bookings> bookings = bookingRepository.findByHelperId(Long.valueOf(helperId));
        bookings.forEach(book -> book.setActive(false));
        helperRepository.delete(helper);
    }

    /**
     * @param helperDto method that updates the details of user
     */

    @Override
    public void updateMember(HelperDto helperDto) {
        Users users = userRepository.findById(Long.valueOf(helperDto.getUserId())).orElse(null);
        Set<Roles> roles;
        if (users == null) {
            throw new HelperAppException(ErrorConstants.USER_NOT_FOUND_ERROR);
        } else {
            roles = users.getRoles();
        }
        boolean isAdmin = roles.stream().anyMatch(role -> role.getName().equals(SuceessConstants.ROLE_ADMIN));
        if(isAdmin) {
            throw new HelperAppException(ErrorConstants.DELETE_NON_ADMIN_ERROR);
        }
        users.setEmail(users.getEmail());
        users.setGender(helperDto.getGender());
        users.setDateOfBirth(helperDto.getDateOfBirth());
        users.setFirstName(helperDto.getFirstName());
        users.setLastName(helperDto.getLastName());
        userRepository.save(users);
    }


}
