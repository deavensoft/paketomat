package com.deavensoft.paketomat.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private Map< String, Object > model;
}
