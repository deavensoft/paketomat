package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "All users are returned")
    @ApiResponse(responseCode = "404", description = "Users not found")
    @ApiResponse(responseCode = "500", description = "Server fault")
    public List<User> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }
    @PostMapping
    @Operation(summary = "Add new user", description = "Add new user to the database")
    public void save(@RequestBody User u){
        userServiceImpl.save(u);
    }
    @GetMapping(path="/{id}")
    @Operation(summary = "Get user", description = "Get user with specified id")
    public Optional<User> findUserById(@PathVariable(name = "id") long id){
       return userServiceImpl.findUserById(id);
    }
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user", description = "Delete user with specified id")
    public int deleteUser(@PathVariable(name = "id") Long id){
        Optional<User> u = findUserById(id);
        if (u.isEmpty())
            System.out.println("there is no user with id:" + id);
        userServiceImpl.deleteUser(u.get());
        System.out.println("User deleted");
        return 1;
    }
}
