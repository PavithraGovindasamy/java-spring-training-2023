package com.sirius.springenablement.ticket_booking.entity;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "theatres")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;



    @ManyToOne
    @JoinColumn(name = "location_id")
    private Locations location;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private List<Shows> shows;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public List<Shows> getShows() {
        return shows;
    }

    public void setShows(List<Shows> shows) {
        this.shows = shows;
    }


}

