package com.sirius.springenablement.ticket_booking.entity;
import  jakarta.persistence.*;
import lombok.Data;

@Entity(name = "locations")
@Data
@Table
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;

    @Column(name = "is_prime")
    private boolean isPrime;

}
