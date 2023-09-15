package com.sirius.springenablement.ticket_booking.services;
import java.util.Date;
public class BookingRequestDto {
    private Long userId;
    private String movieName;
    private String location;

    private String timeslot;
    private java.time.LocalDate date;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.time.LocalDate getBookingDate() {
        return date;
    }

    public String getTimeSlot() {

        return timeslot;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }
}

