package com.deavensoft.paketomat.center.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id")
    private  Long id;
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "paketomat_id")
    private Paketomat paketomat;
    @Column(name = "code", unique = true)
    private Long code;
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;
}
