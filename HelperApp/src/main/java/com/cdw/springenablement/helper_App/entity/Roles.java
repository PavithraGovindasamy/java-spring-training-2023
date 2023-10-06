package com.cdw.springenablement.helper_App.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that stores the roles details
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Roles {

    @PrePersist
    protected  void onCreate(){
    }

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "roles",cascade ={ CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Users> user=new HashSet<>();

    public Roles(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
