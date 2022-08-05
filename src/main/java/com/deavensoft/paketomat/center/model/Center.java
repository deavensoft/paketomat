package com.deavensoft.paketomat.center.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "center")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "center")
    private List<Package> packages;
    @OneToMany(mappedBy = "center")
    public static List<City> cities;
}
