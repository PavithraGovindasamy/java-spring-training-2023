package com.cdw.springenablement.helperapp.constants;

public class SuceessConstants {
    public static final String APPROVED_SUCCESSFULLY_MESSAGE = "Approved successfully";

    public static final String REJECTED_SUCCESSFULLY_MESSAGE = "Rejected Successfully";
    public static final String MEMBER_ADDED_SUCCESSFULLY_MESSAGE = "Member added successfully";
    public static final String RESIDENT_REMOVED_SUCCESSFULLY_MESSAGE = "Resident removed successfully";
    public static final String HELPER_REMOVED_SUCCESSFULLY_MESSAGE = "Helper removed successfully";
    public static final String UPDATED_SUCCESSFULLY_MESSAGE = "Updated Successfully";
    public static final String BOOKED_SUCCESSFULLY_MESSAGE = "Booked Slot Successfully";
    public static final String LOGGEDOUT_SUCCESSFULLY_MESSAGE = "Logged out Successfully";
    public static final String USER_REGISTERED_SUCCESSFULLY_MESSAGE = "User Registered Successfully";


    public static final String AUTHENTICATION_SUCCESSFULL_MESSSAGE = "Authentication successful";


    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_REGISTERED = "registered";

    public static final String STATUS_REJECTED = "rejected";


    public static final String NO_USER_REJECT_MESSAGE = "No users to reject";

    public static final String NO_USER_APPROVE_MESSAGE = "No users to approve";

    public static final String ROLE_ADMIN = "Role_Admin";
    public static final String ROLE_HELPER = "Role_Helper";

    public static final String ROLE_RESIDENT = "Role_Resident";

    public static final String GENDER_PATTERN = "^(FEMALE|MALE|Female|Male|female|male)$";
    public static final String GENDER_PATTERN_MESSAGE = "Gender must be FEMALE or MALE";
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String EMAIL_PATTERN_MESSAGE = "Please provide a valid email";
    public static final String EMAIL_NOT_EMPTY_MESSAGE = "Email cannot be empty";
    public static final String USERNAME_PATTERN_MESSAGE = "It must contain only letters ";
    public static final String USERNAME_PATTERN = "[a-zA-Z]+";


    public static final long TIME_LIMIT = 3 * 60 * 60 * 1000;


}