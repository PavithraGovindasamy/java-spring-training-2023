package com.sirius.springenablement.meetingScheduler.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import java.util.List;

/**
 * Class which has basic columns of rooms
 * @author pavithra
 */
@Entity
@Table(name="rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "room_name")
    private  String roomName;

    @Column(name = "room_capacity")
    private Integer roomCapacity;

    /**
     * Empty construtor
     */
    public  Rooms(){

    }

    /**
     * Constructor which gets basic details of rooms
     * @param roomName
     * @param roomCapacity
     */

    public Rooms(String roomName, int roomCapacity) {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    /**
     * Mapping with time_slots
     */

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
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
