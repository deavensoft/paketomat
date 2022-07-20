package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("user")
public interface UserRepository extends JpaRepository<User, Long> {

}
