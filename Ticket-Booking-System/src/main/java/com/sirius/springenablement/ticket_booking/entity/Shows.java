package com.sirius.springenablement.ticket_booking.entity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import java.time.LocalTime;
import java.time.LocalDate;
@jakarta.persistence.Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@Table(name = "shows")
public class Shows {
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id")
    private int id;

  @Column(name = "movie_name")
   private  String movieName;

   @Column(name = "description")
    private String description;

    @Column(name="poster")
    private String poster;

    @Column(name = "time_slot")
    private String timeSlot;

    @Column(name = "date")
    private LocalDate date;


    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Tickets> tickets;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public java.util.List<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(java.util.List<Tickets> tickets) {
        this.tickets = tickets;
    }

    public com.sirius.springenablement.ticket_booking.entity.Locations getLocation() {
        return location;
    }

    public void setLocation(com.sirius.springenablement.ticket_booking.entity.Locations location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Shows{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", description='" + description + '\'' +
                ", poster='" + poster + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                '}';
    }

}
