package com.sirius.springenablement.ticket_booking.services;
import com.sirius.springenablement.ticket_booking.entity.Users;
import com.sirius.springenablement.ticket_booking.entity.Roles;
public  interface UserService {


    void addToUser(String username,String roleName);

}