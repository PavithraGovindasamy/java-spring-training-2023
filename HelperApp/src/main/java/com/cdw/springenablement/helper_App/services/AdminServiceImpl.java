package com.cdw.springenablement.helper_App.services;

import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.entity.*;
import com.cdw.springenablement.helper_App.repository.*;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<ApprovalDto> getApprovalRequest() {
        List<Users> usersToApprove = userRepository.findByApproved("registered");
        List<ApprovalDto> approvalDtos=new ArrayList<>();
        for(Users user:usersToApprove){
            System.out.println(user.getId());
            ApprovalDto approvalDto=new ApprovalDto();
            approvalDto.setUserId(user.getId());
            approvalDto.setEmail(user.getEmail());
                approvalDto.setGender(user.getGender());
                approvalDto.setFirstName(user.getFirstName());
                approvalDto.setLastName(user.getLastName());
                approvalDto.setDateOfBirth(user.getDateOfBirth());
                approvalDtos.add(approvalDto);
        }

        return approvalDtos;
    }

    public void  approveRequest(){
        List<Users> usersToApprove = userRepository.findByApproved("registered");
        usersToApprove.forEach(user -> user.setApproved("approved"));
        userRepository.saveAll(usersToApprove);

    }

    @Override
    public void addNewMember(UserDto userDto) throws Exception {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            try {
                throw new Exception("User with this email already exists.");
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

        user.setApproved("registered");

        Set<Roles> userRoles = new HashSet<>();

        List<String> roleNames = userDto.getRole();
        for (String roleName : roleNames) {

            Roles role = rolesRepository.findByName(roleName);
            if(role==null){
                throw new Exception("Role not found");
            }
            System.out.println("roles"+ role);
            userRoles.add(role);

        }
        user.setRoles(userRoles);


        userRepository.save(user);
        System.out.println("USer added");



    }

    @Override
    public void removeResident(Integer residentId) throws Exception {
        Users user = userRepository.findById(Long.valueOf(residentId)).orElse(null);
        if (user != null) {
            Set<Roles> roles = user.getRoles();
            for (Bookings booking : user.getBookings()) {
                Long timeslotId=booking.getTimeSlot().getId();
                TimeSlot timeSlot=timeSlotRepository.findById(timeslotId).orElse(null);
                timeSlot.setBooked(false);
                bookingRepository.delete(booking);
            }
            boolean isResident = roles.stream().anyMatch(role -> role.getName().equals("Role_Resident"));
            if (isResident) {
                userRepository.deleteById(Long.valueOf(residentId));
            } else {
                throw  new Exception("cannot delete ..not a resident");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    @Override
    public void removeHelper(Integer helperId) throws Exception {
        Helper helper = helperRepository.findById(Long.valueOf(helperId)).orElse(null);
        if (helper == null) {
            throw new Exception("Helper not found.");
        }

        helper.getTimeSlots().clear();

        List<Bookings> appointments = bookingRepository.findByHelper(helper);
        for (Bookings appointment : appointments) {
            bookingRepository.delete(appointment);
        }

        int userId = Math.toIntExact(helper.getId());
        Users user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }

        helperRepository.delete(helper);
    }


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
