package com.sirius.springenablement.ticket_booking.dto;
import java.util.Date;
public class BookingRequestDto {
    private Long userId;
    private String movieName;
    private String location;

    private String timeslot;
    private java.time.LocalDate date;

    private int noOfTickets;

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

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

    private String theatreName;

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public java.time.LocalDate getDate() {
        return date;
    }
}

