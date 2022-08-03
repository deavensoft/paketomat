package com.deavensoft.paketomat.center.dto;

import com.deavensoft.paketomat.center.model.Paketomat;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PackageDTO {
    @JsonProperty("id_dto")
    private Long id_dto;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("user")
    private User user;
    @JsonProperty("paketomat")
    private Paketomat paketomat;
}
