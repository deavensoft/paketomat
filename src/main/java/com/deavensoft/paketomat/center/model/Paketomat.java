package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;
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

    @Column(name = "addr", columnDefinition = "serial")
    @Generated(GenerationTime.INSERT)
    private Long addr;
    @JsonIgnore
    @OneToMany(mappedBy = "paketomat")
    private List<Package> packages;

    public Paketomat(City c) {
        this.city = c;
        this.packages = new ArrayList<>();
    }

    public void reserveSlot(Package newPackage) {
        packages.add(newPackage);
        newPackage.setPaketomat(this);
        newPackage.setStatus(Status.TO_DISPATCH);
    }
    public void freeBox(Package oldPackage){
        packages.remove(oldPackage);
        oldPackage.setPaketomat(null);
    }
}
