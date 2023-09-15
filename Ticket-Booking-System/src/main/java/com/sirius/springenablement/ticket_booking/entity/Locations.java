package com.sirius.springenablement.ticket_booking.entity;
import  jakarta.persistence.*;

@Entity(name = "locations")
@lombok.Data
@Table
public class Locations {
    @Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @jakarta.persistence.Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "is_prime")
    private boolean isPrime;

}
