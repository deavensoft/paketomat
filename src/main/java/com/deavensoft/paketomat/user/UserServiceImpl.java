package com.deavensoft.paketomat.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private User user;
    @Autowired
    public UserServiceImpl(@Qualifier("user") User user){
        this.user = user;
    }
    public List<com.deavensoft.paketomat.center.model.User> getAllUsers(){

        return user.findAll();
    }
    public void save(com.deavensoft.paketomat.center.model.User u){
        user.save(u);
    }
    public int deleteUser(com.deavensoft.paketomat.center.model.User u){
        user.delete(u);
        return 1;
    }
    public Optional<com.deavensoft.paketomat.center.model.User> findUserById(Long id){
        return user.findById(id);
    }

}
