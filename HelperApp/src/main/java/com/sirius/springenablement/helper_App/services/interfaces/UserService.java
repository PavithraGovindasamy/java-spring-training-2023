package com.sirius.springenablement.helper_App.services.interfaces;

import com.sirius.springenablement.helper_App.dto.UserDto;

public  interface UserService {

    
    void addToUser(String username,String roleName);

    void registerUser(UserDto userDto);
}