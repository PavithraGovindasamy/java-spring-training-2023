package com.sirius.springenablement.ticket_booking.dto;
import java.time.LocalDate;
import lombok.Data;
import com.sirius.springenablement.ticket_booking.entity.Roles;
@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String password;
    private Roles roles;
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRoles(Roles roles){
        this.roles=roles;
    }

    public Roles getRoles() {
        return  roles;
    }


}
