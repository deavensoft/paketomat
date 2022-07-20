package com.deavensoft.paketomat.dispatcher;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dispatcher", schema = "public")
public class DispatcherModel {

     @Id
     @Column(name = "id")
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long id;

     @Column(name = "email")
     private String email;

     @Column(name = "name")
     private String name;

     public DispatcherModel(@JsonProperty("id") Long id,@JsonProperty("email") String email, @JsonProperty("name") String name){
          this.id = id;
          this.email = email;
          this.name = name;
     }
}
