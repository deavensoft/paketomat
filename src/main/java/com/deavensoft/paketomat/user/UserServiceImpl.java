package com.deavensoft.paketomat.user;


import com.deavensoft.paketomat.center.PackageService;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PackageService packageService;

    @Autowired

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public void save(com.deavensoft.paketomat.center.model.User u) {
        userRepository.save(u);
    }

    public int deleteUser(User u) {
        userRepository.delete(u);
        return 1;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }



}
