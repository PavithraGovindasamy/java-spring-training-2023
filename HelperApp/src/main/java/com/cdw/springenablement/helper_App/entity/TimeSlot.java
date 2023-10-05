package com.cdw.springenablement.helper_App.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "timeslot")
@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isBooked")
    private Boolean isBooked;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;


    @Column(name = "date")
    private LocalDate date;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "timeslot_helper",
            joinColumns = @JoinColumn(name = "timeslot_id"),
            inverseJoinColumns = @JoinColumn(name = "helper_id")
    )
    private Set<Helper> helpers;


    public Set<Helper> getHelpers() {
        return helpers;
    }

    public void setHelpers(Set<Helper> helpers) {
        this.helpers = helpers;
    }

    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL)
    private List<Bookings> bookings;

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }
}
