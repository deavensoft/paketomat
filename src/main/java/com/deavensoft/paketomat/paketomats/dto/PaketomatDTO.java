package com.deavensoft.paketomat.paketomats.dto;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaketomatDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("city")
    private City city;
    @JsonProperty("packages")
    private ArrayList<Package> packages;
}


