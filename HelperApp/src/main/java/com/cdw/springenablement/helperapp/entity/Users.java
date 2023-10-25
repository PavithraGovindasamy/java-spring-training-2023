package com.cdw.springenablement.helperapp.entity;
import java.time.LocalDate;

import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
@Entity
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    @Size(min = 3)
    @Pattern(regexp = SuceessConstants.USERNAME_PATTERN, message = SuceessConstants.USERNAME_PATTERN_MESSAGE)
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @Size(min = 1)
    @Pattern(regexp = SuceessConstants.USERNAME_PATTERN, message = SuceessConstants.USERNAME_PATTERN_MESSAGE)
    private String lastName;

    @Column(name="dob")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date must be in the past")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @NotNull
    @Pattern(regexp = SuceessConstants.GENDER_PATTERN, message = SuceessConstants.GENDER_PATTERN_MESSAGE)
    private String gender;

    @Column(name = "email")
    @NotNull
    @NotEmpty(message = SuceessConstants.EMAIL_NOT_EMPTY_MESSAGE)
    @Pattern(regexp = SuceessConstants.EMAIL_PATTERN, message = SuceessConstants.EMAIL_PATTERN_MESSAGE)
    private String email;


    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "approved")
    private String  approved;


    @ManyToMany(fetch = FetchType.EAGER,cascade ={ CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles=new HashSet<>();


    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Bookings> bookings;

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public Users() {

    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public Users(String firstName, String lastName, LocalDate dateOfBirth, String email, String password, String gender, Set<Roles> roles)  {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth=dateOfBirth;
        this.email = email;
        this.gender=gender;
        this.password=password;
        this.roles=roles;
    }

    public java.util.Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(java.util.Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Collection<SimpleGrantedAuthority> authorities=new java.util.ArrayList<>();
        roles.stream().forEach(roles->authorities.add(new SimpleGrantedAuthority(roles.getName())));
        return List.of(new SimpleGrantedAuthority(authorities.toString()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.time.LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(java.time.LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
