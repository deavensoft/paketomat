package com.deavensoft.paketomat.center.dto;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PaketomatDTO {

    private UUID id;
    private City city;
    private int size;
    private ArrayList<Package> packages;

}
