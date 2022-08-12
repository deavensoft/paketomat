package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     List<User> getAllUsers();
     void save(User u);
     int deleteUser(User u);
     Optional<User> findUserById(Long id);




}
