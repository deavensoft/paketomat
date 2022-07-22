package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserServiceImpl userServiceImpl;
    @Autowired
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    @Operation(summary = "Get users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "All users are returned")
    public List<User> getAllUsers(){
        logger.info("All users are returned");
        return userServiceImpl.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Add new user", description = "Add new user to the database")
    @ApiResponse(responseCode = "200", description = "New user added")
    public void save(@RequestBody User u){
        userServiceImpl.save(u);
        logger.info("New user added to the database");
    }

    @GetMapping(path="/{id}")
    @Operation(summary = "Get user", description = "Get user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id returned")
    public Optional<User> findUserById(@PathVariable(name = "id") long id){
        Optional<User> u = userServiceImpl.findUserById(id);
        if(u.isEmpty()){
            String mess = "There is no user with id " + id;
            logger.info(mess);
        } else{
            String mess = "User with id " + id + " is returned";
            logger.info(mess);
        }
       return u;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user", description = "Delete user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id deleted")
    public int deleteUser(@PathVariable(name = "id") Long id){
        Optional<User> u = findUserById(id);
        try {
            String mess = "User with id " + id + " is deleted";
            logger.info(mess);
            userServiceImpl.deleteUser(u.get());
        } catch (NoSuchElementException e){
            String mess = "There is no user with id " + id;
            logger.error(mess);
        }
        return 1;
    }
}
