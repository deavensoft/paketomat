package com.deavensoft.paketomat.paketomats.dto;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class PaketomatDTO {

    private Long id;
    private City city;
    private int size;
    private ArrayList<Package> packages;

}
