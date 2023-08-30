package com.sirius.springenablement.meetingScheduler.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * class which has basic column of time_slots
 * @author pavithra
 */
@Entity
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "date")
    private LocalDate date;


    @Column(name = "start_time")
    private LocalTime startTime;

   @Column(name = "end_time")
   private LocalTime endTime;


    @Column(name = "description")
    private String description;

    /**
     * Default constructor.
     */
    public  TimeSlot(){

    }

    /**
     *
     * @param date
     * @param startTime
     * @param endTime
     * @param description
     */
    public TimeSlot(LocalDate date,LocalTime startTime, LocalTime endTime, String description) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    // mapping with employee
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "employee_id")
    public  Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @ManyToMany(fetch=FetchType.LAZY
            ,cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "rooms_timeslots",
            joinColumns=@JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "rooms_id")
    )
    public List<Rooms> rooms;


    public List<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(List<Rooms> rooms) {
        this.rooms = rooms;
    }

    // mapping teams with time_slot
    @ManyToMany(fetch =FetchType.LAZY,
    cascade = {CascadeType.DETACH,CascadeType.MERGE,
    CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name="teams_timeslots",
            joinColumns=@JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    public  List<Teams> teams;

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return startTime;
    }

    public void setStart_time(LocalTime start_time) {
        this.startTime = start_time;
    }

    public LocalTime getEnd_time() {
        return endTime;
    }

    public void setEnd_time(LocalTime end_time) {
        this.endTime = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    private List<Rooms> newRooms = new ArrayList<>();
//
//    public void addRoom(Rooms newRooms) {
//        rooms.add(newRooms);
//    }
}
