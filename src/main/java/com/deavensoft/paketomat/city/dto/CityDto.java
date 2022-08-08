package com.deavensoft.paketomat.city.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityDto {
    @JsonProperty("geonameid")
    private Integer geonameid;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("name")
    private String name;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;

}
