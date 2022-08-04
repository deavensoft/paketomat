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
    @ManyToOne()
    @JoinColumn(name="city", nullable = false)
    private City city;

    @Column(name = "serial_number", unique = true)
    public Long addr;
    public static Long serialNumber = 1L;
    @JsonIgnore
    @OneToMany(mappedBy = "paketomat")
    private List<Package> packages;

    public Paketomat(City c, Long aLong) {
        this.city= c;
        this.addr = aLong;
        this.packages = new ArrayList<>();
    }


    public void reserveSlot(Package newPackage){
        packages.add(newPackage);
        newPackage.setPaketomat(this);
        newPackage.setStatus(Status.TO_DISPATCH);
    }

}
