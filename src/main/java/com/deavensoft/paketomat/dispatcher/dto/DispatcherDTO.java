package com.deavensoft.paketomat.dispatcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
}
