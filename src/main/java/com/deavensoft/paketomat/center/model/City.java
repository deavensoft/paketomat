package com.deavensoft.paketomat.center.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "city", schema = "public")
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
    @Transient
    private List<Paketomat> paketomats;

}
