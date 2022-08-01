package com.deavensoft.paketomat.center.dto;

import com.deavensoft.paketomat.center.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackageDTO {

    private Long id;
    private Status status;
    private Long sender;
    private Long reciever;

}
