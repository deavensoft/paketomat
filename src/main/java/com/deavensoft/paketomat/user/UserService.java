package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers();
    public void save(User u);
    public int deleteUser(User u);
    public Optional<User> findUserById(Long id);

}
