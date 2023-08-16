package com.sirius.springenablement.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "room_name")
    private  String roomName;

    @Column(name = "room_capacity")
    private String roomCapacity;


    public  Rooms(){

    }

    public Rooms(String roomName, String roomCapacity) {

        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    //mapping with time_slot table

    @ManyToMany(fetch=FetchType.LAZY
            ,cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "rooms_timeslots",
            joinColumns=@JoinColumn(name = "rooms_id"),
            inverseJoinColumns = @JoinColumn(name = "time_slot_id")
    )
    public List<TimeSlot> timeSlots;


    public List<TimeSlot> getRooms() {
        return timeSlots;
    }

    public void setRooms(List<TimeSlot> timeSlots) {
        this.timeSlots=timeSlots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(String roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", roomCapacity='" + roomCapacity + '\'' +
                '}';
    }
}
