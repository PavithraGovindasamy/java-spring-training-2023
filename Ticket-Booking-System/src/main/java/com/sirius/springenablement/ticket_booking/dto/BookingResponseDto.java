package com.sirius.springenablement.ticket_booking.dto;
import java.time.LocalDate;
public class BookingResponseDto {
    private Long userId;
    private String movieName;
    private java.time.LocalDate bookingDate;
    private String location;
    private int id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    // Getters and setters for all fields

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

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(java.time.LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setBookingId(int id) {
        this.id=id;
    }
}
