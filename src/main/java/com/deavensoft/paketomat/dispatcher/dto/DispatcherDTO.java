package com.deavensoft.paketomat.dispatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DispatcherDTO {
    private Long id;
    private String email;
    private String name;
}
