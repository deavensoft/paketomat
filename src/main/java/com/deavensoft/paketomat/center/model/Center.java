package com.deavensoft.paketomat.center.model;

import lombok.*;


import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Center {
    private ArrayList<Package> packages;
    private ArrayList<City> cities;
}
