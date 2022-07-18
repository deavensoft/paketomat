package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;


@Component
@NoArgsConstructor
@Data
@Setter
@Getter
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private  UUID id;
    @Column(name = "status")
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User reciever;

    public Package(@JsonProperty("id") UUID id,@JsonProperty("Status") Status status){
        this.id = id;
        this.status = status;
    }
}
