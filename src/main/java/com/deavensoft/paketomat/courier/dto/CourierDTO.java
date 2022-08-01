package com.deavensoft.paketomat.courier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CourierDTO {
    private Long id;
    private String email;
    private String name;

}
