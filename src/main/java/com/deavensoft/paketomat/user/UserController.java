package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.exceptions.NoSuchCourierException;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;


    @GetMapping
    @Operation(summary = "Get users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "All users are returned")
    public List<User> getAllUsers(){
        log.info("All users are returned");
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Add new user", description = "Add new user to the database")
    @ApiResponse(responseCode = "200", description = "New user added")
    public void save(@RequestBody User u){
        userService.save(u);
        log.info("New user added to the database");
    }

    @GetMapping(path="/{id}")
    @Operation(summary = "Get user", description = "Get user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id returned")
    public Optional<User> findUserById(@PathVariable(name = "id") long id) throws NoSuchUserException {
        Optional<User> u = userService.findUserById(id);
        if(u.isEmpty()){
            throw new NoSuchUserException("There is no user with id " + id, HttpStatus.OK, 200);
        } else{
            String mess = "User with id " + id + " is returned";
            log.info(mess);
        }
       return u;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user", description = "Delete user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id deleted")
    public int deleteUser(@PathVariable(name = "id") Long id) throws NoSuchUserException {
        Optional<User> u = findUserById(id);
        try {
            String mess = "User with id " + id + " is deleted";
            log.info(mess);
            userService.deleteUser(u.get());
        } catch (NoSuchElementException e){
            throw new NoSuchUserException("User with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
}
