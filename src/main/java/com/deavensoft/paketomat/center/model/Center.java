package com.deavensoft.paketomat.center.model;

import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Center {
    private ArrayList<Package> packages;
    public static List<City> cities;


}
