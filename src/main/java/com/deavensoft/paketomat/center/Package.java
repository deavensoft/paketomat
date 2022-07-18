package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.UUID;


@Component
@NoArgsConstructor
@Data
@Setter
@Getter
public class Package {

    private  UUID id;
    private Status status;
    public Package(@JsonProperty("id") UUID id,@JsonProperty("Status") Status status){
        this.id = id;
        this.status = status;
    }

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false)
    private User sender;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "reciever", nullable = false)
    private User reciever;
}
