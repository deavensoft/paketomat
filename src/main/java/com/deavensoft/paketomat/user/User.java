package com.deavensoft.paketomat.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("user")
public interface User extends JpaRepository<com.deavensoft.paketomat.center.model.User, Long> {

}
