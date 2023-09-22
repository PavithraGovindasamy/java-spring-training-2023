package com.sirius.springenablement.ticket_booking.entity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;

@jakarta.persistence.Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@Table(name = "shows")
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    private List<Bookings> bookings;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
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
