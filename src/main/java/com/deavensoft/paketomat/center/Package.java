package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import lombok.*;



import javax.persistence.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Package")
public class Package {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private UUID id;
    @Column(name="status")
    private Status status;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false)
    private User sender;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "reciever", nullable = false)
    private User reciever;
}
