package com.deavensoft.paketomat.center.model;

import lombok.*;


import java.util.ArrayList;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Center {
    private ArrayList<Package> packages;
    private ArrayList<City> cities;

    public Center (){

    }
}
