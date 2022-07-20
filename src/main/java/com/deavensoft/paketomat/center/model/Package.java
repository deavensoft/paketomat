package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@Table(name = "package", schema="public")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private  Long id;
    @Column(name = "status")
    private Status status;

    @Column(name="reciever")
    private Long sender;

    @Column(name="sender")
    private Long reciever;

    public Package(@JsonProperty("id") Long id,@JsonProperty("Status") Status status,@JsonProperty("reciever") Long reciever,
                   @JsonProperty("sender")Long sender){
        this.id = id;
        this.status = status;
        this.reciever=reciever;
        this.sender=sender;
    }
}
