package com.cdw.springenablement.helper_App.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "helpers")
@Getter
@Setter
@NoArgsConstructor
public class Helper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "specialization")
    private String specialization;




    @ManyToMany(mappedBy = "helpers")
    private Set<TimeSlot> timeSlots;



//    @OneToMany(mappedBy = "helper", cascade = CascadeType.ALL)
//    private List<TimeSlot> timeSlots;

}
