package com.deavensoft.paketomat.center.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "population")
    private int population;
    @Column(name = "x")
    private double latitude;
    @Column(name = "y")
    private double longitude;
    @Transient
    private ArrayList<Paketomat> paketomats;


}
