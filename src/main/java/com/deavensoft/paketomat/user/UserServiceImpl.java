package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(@Qualifier("user") UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers(){

        return userRepository.findAll();
    }
    public void save(com.deavensoft.paketomat.center.model.User u){
        userRepository.save(u);
    }
    public int deleteUser(User u){
        userRepository.delete(u);
        return 1;
    }
    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

}
