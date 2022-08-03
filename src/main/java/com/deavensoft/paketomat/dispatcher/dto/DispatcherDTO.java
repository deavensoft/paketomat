package com.deavensoft.paketomat.dispatcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("emial")
    private String email;
    @JsonProperty("name")
    private String name;
}
