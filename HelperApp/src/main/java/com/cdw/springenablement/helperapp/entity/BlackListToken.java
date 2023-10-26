package com.cdw.springenablement.helperapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that stores the blacklisted token
 * @Author pavithra
 */
@Entity
@Table(name = "blacklist_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlackListToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;

}
