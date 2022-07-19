package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("user")
public interface iUser extends JpaRepository<User, Long> {

}
