package com.deavensoft.paketomat.user;

import com.deavensoft.paketomat.user.dto.UserDTO;
import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import com.deavensoft.paketomat.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    private UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get users", description = "Get all users")
    @ApiResponse(responseCode = "200", description = "All users are returned")
    public List<UserDTO> getAllUsers(){

        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(userMapper.userToUserDTO(user));
        }
        log.info("All users are returned");
        return userDTOS;

    }

    @PostMapping
    @Operation(summary = "Add new user", description = "Add new user to the database")
    @ApiResponse(responseCode = "200", description = "New user added")
    public void save(@RequestBody UserDTO u){
        User user = userMapper.userDTOToUser(u);
        userService.save(user);
        log.info("New user added to the database");
    }

    @GetMapping(path="/{id}")
    @Operation(summary = "Get user", description = "Get user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id returned")
    public UserDTO findUserById(@PathVariable(name = "id") long id) throws NoSuchUserException {
        Optional<User> u = userService.findUserById(id);


        if(u.isEmpty()){
            throw new NoSuchUserException("There is no user with id " + id, HttpStatus.OK, 200);
        } else{
            User user = u.get();
            UserDTO userDTO = userMapper.userToUserDTO(user);
            String mess = "User with id " + id + " is returned";
            log.info(mess);

            return userDTO;
        }

    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user", description = "Delete user with specified id")
    @ApiResponse(responseCode = "200", description = "User with specified id deleted")
    public int deleteUser(@PathVariable(name = "id") Long id) throws NoSuchUserException {
        Optional<User> u = userService.findUserById(id);

        if (u.isEmpty()) {
            throw new NoSuchUserException("There is no user with id " + id, HttpStatus.OK, 200);
        }else {
            User user = u.get();
            userService.deleteUser(user);
            log.info("User deleted");
            return 1;
        }
    }
}
