package com.sirius.springenablement.ticket_booking.entity;
import  jakarta.persistence.*;
@Table
@Entity(name = "bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Shows show;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Tickets ticket;



    @Column(name = "status")
    private String bookingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public com.sirius.springenablement.ticket_booking.entity.Users getUser() {
        return user;
    }

    public void setUser(com.sirius.springenablement.ticket_booking.entity.Users user) {
        this.user = user;
    }

    public com.sirius.springenablement.ticket_booking.entity.Shows getShow() {
        return show;
    }

    public void setShow(com.sirius.springenablement.ticket_booking.entity.Shows show) {
        this.show = show;
    }

    public com.sirius.springenablement.ticket_booking.entity.Tickets getTicket() {
        return ticket;
    }

    public void setTicket(com.sirius.springenablement.ticket_booking.entity.Tickets ticket) {
        this.ticket = ticket;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
