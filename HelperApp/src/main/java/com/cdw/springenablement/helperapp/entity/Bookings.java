package com.cdw.springenablement.helperapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that stores the booking details
 * @Author pavithra
 */
@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade ={ CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "helper_id")
    private Long helperId;

    @ManyToOne(fetch = FetchType.EAGER,cascade ={ CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "timeslot_id")
    @NotNull(message = "TimeSlot ID cannot be null")
    private TimeSlot timeSlot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getHelperId() {
        return helperId;
    }

    public void setHelperId(Long helperId) {
        this.helperId = helperId;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
   }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }


}
