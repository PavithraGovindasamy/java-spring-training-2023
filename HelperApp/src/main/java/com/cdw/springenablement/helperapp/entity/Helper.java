package com.cdw.springenablement.helperapp.entity;


import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = SuceessConstants.USERNAME_PATTERN, message = SuceessConstants.USERNAME_PATTERN_MESSAGE)
    private String specialization;



}
