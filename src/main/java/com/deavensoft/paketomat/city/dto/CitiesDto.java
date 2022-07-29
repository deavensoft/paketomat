package com.deavensoft.paketomat.city.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CitiesDto {
    @JsonProperty("total_cities")
    private Integer totalCities;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("cities")
    private List<CityDto> cities = null;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("status")
    private String status;
}
