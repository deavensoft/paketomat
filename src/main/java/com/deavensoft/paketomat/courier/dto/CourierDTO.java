package com.deavensoft.paketomat.courier.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierDTO {
    @JsonProperty("id_courier_dto")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;


}
