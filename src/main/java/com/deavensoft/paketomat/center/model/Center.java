package com.deavensoft.paketomat.center;

import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Center {
    private ArrayList<Package> packages;
    private ArrayList<City> cities;
}
