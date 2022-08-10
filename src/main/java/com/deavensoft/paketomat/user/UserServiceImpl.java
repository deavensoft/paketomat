package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.CenterRepository;
import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.CenterServiceImpl;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CenterService centerService;
    @Autowired
    public UserServiceImpl(@Qualifier("user") UserRepository userRepository,CenterService centerService){
        this.userRepository = userRepository;
        this.centerService=centerService;
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

    public void payPackage(Long id)
    {
       centerService.payment(id, Paid.PAID);
    }

}
