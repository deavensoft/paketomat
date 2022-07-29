package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paketomats", schema = "public")
public class Paketomat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "city")
    private Long city;
    @Transient
    private final int size = 5;
    @Transient
    private ArrayList<Package> packages= new ArrayList<>(size);
    public Paketomat(@JsonProperty("id") Long id, @JsonProperty("city") Long city){
        this.id = id;
        this.city = city;
    }
}
