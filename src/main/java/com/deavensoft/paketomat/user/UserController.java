package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserServiceImpl userServiceImpl;
    @Autowired
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }
    @PostMapping
    public void save(@RequestBody User u){
        userServiceImpl.save(u);
    }
    @GetMapping(path="{id}")
    public Optional<User> findUserById(@PathVariable(name = "id") long id){
       return userServiceImpl.findUserById(id);
    }
    @DeleteMapping(path = "{id}")
    public int deleteUser(@PathVariable(name = "id") Long id){
        Optional<User> u = findUserById(id);
        if (u.isEmpty())
            System.out.println("there is no user with id:" + id);
        userServiceImpl.deleteUser(u.get());
        System.out.println("User deleted");
        return 1;
    }
}
