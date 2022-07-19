package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        userService.save(u);
    }
    @GetMapping(path="{id}")
    public Optional<User> findUserById(@PathVariable(name = "id") long id){
       return userService.findUserById(id);
    }
    @DeleteMapping(path = "{id}")
    public int deleteUser(@PathVariable(name = "id") Long id){
        Optional<User> u = findUserById(id);
        if (u.isEmpty())
            System.out.println("there is no user with id:" + id);
        userService.deleteUser(u.get());
        System.out.println("User deleted");
        return 1;
    }
}
