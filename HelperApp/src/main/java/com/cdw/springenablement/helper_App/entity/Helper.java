package com.cdw.springenablement.helper_App.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

/**
 * Entity that stores the helper special details
 * @Author pavithra
 */
@Entity
@Table(name = "helpers")
@Getter
@Setter
@NoArgsConstructor
public class Helper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,cascade ={ CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    private Users user;


    @Column(name = "specialization")
    private String specialization;



}
