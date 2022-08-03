package com.deavensoft.paketomat.center.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Center {
    private List<Package> packages;
    public static List<City> cities;


}
