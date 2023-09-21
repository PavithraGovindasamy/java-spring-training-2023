package com.sirius.springenablement.ticket_booking.dto;
import java.time.LocalDate;
import lombok.Data;
import java.util.List;
import com.sirius.springenablement.ticket_booking.entity.Roles;
@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String password;
    private List<Roles> role;
    public java.time.LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public java.util.List<com.sirius.springenablement.ticket_booking.entity.Roles> getRole() {
        return role;
    }

    public void setRoles(java.util.List<com.sirius.springenablement.ticket_booking.entity.Roles> role) {
        this.role = role;
    }

    public void setDateOfBirth(java.time.LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


}
