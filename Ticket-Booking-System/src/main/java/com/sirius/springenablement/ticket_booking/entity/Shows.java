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

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "show", cascade = CascadeType.REMOVE)
    private java.util.List<Bookings> bookings;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    public com.sirius.springenablement.ticket_booking.entity.Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(com.sirius.springenablement.ticket_booking.entity.Theatre theatre) {
        this.theatre = theatre;
    }

    @Column(name="availableCount")
    private int availableCount;

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

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
