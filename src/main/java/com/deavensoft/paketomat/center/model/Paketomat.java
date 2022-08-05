package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paketomat")
public class Paketomat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "city", nullable = false)
    private City city;

    @Column(name = "serial_number", unique = true)
    private Long addr;
    private static Long serialNumber = 1L;
    @JsonIgnore
    @OneToMany(mappedBy = "paketomat")
    private List<Package> packages;

    public Paketomat(City c) {
        this.city = c;
        this.addr = serialNumber++;
        this.packages = new ArrayList<>();
    }

    public void reserveSlot(Package newPackage) {
        packages.add(newPackage);
        newPackage.setPaketomat(this);
        newPackage.setStatus(Status.TO_DISPATCH);
    }

}
