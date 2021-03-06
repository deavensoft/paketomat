package com.deavensoft.paketomat.center.model;

import lombok.*;

import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paketomat {

    private UUID id;
    private City city;
    private int size;
    private ArrayList<Package> packages;
}
