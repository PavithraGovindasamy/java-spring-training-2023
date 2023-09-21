package com.sirius.springenablement.ticket_booking.entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Set;
@jakarta.persistence.Entity
@Table(name = "roles")

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
public class Roles {

    @jakarta.persistence.PrePersist
    protected  void onCreate(){

    }

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @jakarta.persistence.ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Users> user=new java.util.HashSet<>();
    public Roles(String name){
        this.name=name;
    }

}
