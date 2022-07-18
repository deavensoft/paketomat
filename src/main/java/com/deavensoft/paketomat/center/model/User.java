package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    public User(@JsonProperty("id") Long id, @JsonProperty("email") String email, @JsonProperty("name") String name,@JsonProperty("address") String address){
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
    }

}
