package com.cdw.springenablement.helperapp.constants;

public class ErrorConstants {

    public static final String USER_NOT_FOUND_ERROR = "User Not found";
    public static final String USER_ALREADY_EXISTS_ERROR = "User with this email already exists.";

    public static final String HELPER_NOT_FOUND_ERROR = "Helper not found.";

    public static final String DELETE_NON_RESIDENT_ERROR = "Cannot delete, not a resident.";

    public static final String INVALID_ROLE_ERROR = "Invalid role: ";
    public static final String CANNOT_REGISTER_ADMIN_ERROR = "Cannot register Admin";
    public static final String CANNOT_BOOK_NON_RESIDENT_ERROR = "Cannot book slot for non-resident";
    public static final String HELPER_ALREADY_BOOKED_ERROR = "Selected helper is already booked for the specified time slot";
    public static final String SPECIALIZATION_REQUIRED_ERROR = "Specialization is required for helpers.";
    public static final String NO_HELPER_EXISTS_ERROR = "No such helper exists";
    public static final String USER_NOT_FOUND_ERROR_FORMAT = "User not found with ID:";
    public static final String USER_NOT_APPROVED_MESSAGE = "User is not yet approved by admin";
    public static final String USER_REJECTED_MESSAGE = "User is rejected by admin";
}
