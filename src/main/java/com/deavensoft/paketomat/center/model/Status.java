package com.deavensoft.paketomat.center.model;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public enum Status {
    NEW, TO_DISPATCH, IN_PAKETOMAT, DELIVERED, RETURNED
}
