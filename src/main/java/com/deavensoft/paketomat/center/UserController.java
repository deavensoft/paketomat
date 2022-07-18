package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping
    public void save(@RequestBody User u){
       // User user = new User(u.getId(),u.getEmail(),u.getName(),u.getAddress());
        userService.save(u);

    }
}
