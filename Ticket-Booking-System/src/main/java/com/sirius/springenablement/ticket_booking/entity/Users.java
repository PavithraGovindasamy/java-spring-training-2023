package com.sirius.springenablement.ticket_booking.entity;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.sirius.springenablement.ticket_booking.entity.Roles;
@Entity
@Table(name = "users")
@Data
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="dob")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public Users() {

    }

//    @ManyToMany(fetch=FetchType.LAZY
//            ,cascade = {CascadeType.DETACH,CascadeType.MERGE,
//            CascadeType.PERSIST,CascadeType.REFRESH})
//    @JoinTable( name = "users_roles",
//            joinColumns=@JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "roles_id")
//    )
//    public java.util.List<Roles> roles;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Users(String firstName, String lastName,LocalDate dateOfBirth, String email,String password,String gender,Roles roles) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth=dateOfBirth;
        this.email = email;
        this.gender=gender;
        this.password=password;
        this.role=roles;
    }


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
