package com.deavensoft.paketomat.center.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;
    @JsonIgnore
    @Column(name = "date")
    private LocalDateTime date;
    @Column(name = "paid")
    private Paid paid;
}
