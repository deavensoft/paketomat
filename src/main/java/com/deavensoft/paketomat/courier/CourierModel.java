package com.deavensoft.paketomat.courier;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courier", schema = "public")
public class CourierModel {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    public CourierModel(@JsonProperty("id") Long id, @JsonProperty("email") String email, @JsonProperty("name") String name){
        this.id = id;
        this.email = email;
        this.name = name;

    }
}
