package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "population")
    private int population;
    @Column(name = "x")
    private double latitude;
    @Column(name = "y")
    private double longitude;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

}
