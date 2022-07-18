package com.deavensoft.paketomat.center.model;

import com.deavensoft.paketomat.center.Package;
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
