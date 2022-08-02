package com.deavensoft.paketomat.dispatcher;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "dispatcher")
public class Dispatcher {

     @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     @Column(name = "email")
     private String email;

     @Column(name = "name")
     private String name;
}
