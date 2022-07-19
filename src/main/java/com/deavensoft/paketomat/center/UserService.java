package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private iUser user;
    @Autowired
    public UserService(@Qualifier("user") iUser user){
        this.user = user;
    }
    public List<User> getAllUsers(){

        return user.findAll();
    }
    public void save(User u){
        user.save(u);
    }
    public int deleteUser(User u){
        user.delete(u);
        return 1;
    }
    public Optional<User> findUserById(Long id){
        return user.findById(id);
    }

}
