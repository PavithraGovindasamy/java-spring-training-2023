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
import java.util.ArrayList;
import java.util.List;

/**
 * Class which has basic columns of teams
 * @author pavithra
 */
@Entity
@Table(name = "teams")
public class Teams {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "team_name")
    private  String teamName;


    @Column(name = "team_capacity")
    private Integer teamCapacity;


    public Teams(){

   }

    public Teams( String teamName, Integer teamCapacity) {
        this.teamName = teamName;
        this.teamCapacity = teamCapacity;
    }
    // mapping with employees

    @ManyToMany(fetch=FetchType.LAZY
            ,cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "employee_teams",
            joinColumns=@JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    public List<Employee> employee;

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

      // mapping teams with time_slot
    @ManyToMany(fetch =FetchType.LAZY,
        cascade = {CascadeType.DETACH,CascadeType.MERGE,
                CascadeType.PERSIST,CascadeType.REFRESH})
       @JoinTable(name="teams_timeslots",
        joinColumns=@JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "time_slot_id"))
        public  List<TimeSlot> timeSlots;

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamCapacity() {
        return teamCapacity;
    }

    public void setTeamCapacity(int teamCapacity) {
        this.teamCapacity = teamCapacity;

    }

    public  void addEmployee(Employee theEmployee){
        if(employee==null){
            employee=new ArrayList<>();
        }
        employee.add(theEmployee);
    }

    @Override
    public String toString() {
        return "Teams{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamCapacity='" + teamCapacity + '\'' +
                ", employee=" + employee +
                '}';
    }


}
