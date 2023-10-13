package com.cdw.springenablement.helperapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
