package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.dispatcher.DispatcherService;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.user.UserService;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/packages")
@Slf4j
@AllArgsConstructor
public class CenterController {

    private final CenterService centerService;
    private final EmailService emailService;
    private final UserService userService;

    private final DispatcherService dispatcherService;


    @GetMapping
    @Operation(summary = "Get packages", description = "Get all packages")
    @ApiResponse(responseCode = "200", description = "All packages are returned")
    public List<Package> getAllPackages()
    {
        log.info("All packages are returned");
        return centerService.getAllPackages();
    }

    @PostMapping
    @Operation(summary = "Add new package", description = "Add new package to the distributive center")
    @ApiResponse(responseCode = "200", description = "New package added")
    public int savePackage(@RequestBody Package newPackage) throws IOException, PaketomatException {
        newPackage.setStatus(Status.NEW);
        centerService.save(newPackage);
        log.info("New package added to the database");

        Optional<User> user = userService.findUserById(newPackage.getUser().getId());
        if(user.isEmpty()){
            String messages = "User not found";
            log.info(messages);
        } else{
            String messages = "User exist";
            log.info(messages);

            EmailDetails emailDetails = new EmailDetails();
            Map<String, Object> model = new HashMap<>();
            emailDetails.setMsgBody("Package arrived at the distribution center");
            emailDetails.setRecipient(user.get().getEmail());
            emailDetails.setSubject("test");
            model.put("msgBody", emailDetails.getMsgBody());
            emailService.sendMailWithTemplate(emailDetails, model);
            dispatcherService.delieverPackage(newPackage);
            log.info("Package is ready to be dispatched");
            return 1;
        }
        return -1;

    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get package", description = "Get package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id returned")
    public Optional<Package> getPackageById(@PathVariable(name = "id") Long id) throws NoSuchPackageException {
        Optional<Package> p = centerService.findPackageById(id);
        if(p.isEmpty()){
            throw new NoSuchPackageException("There is no package with id " + id, HttpStatus.OK, 200);
        } else{
            String mess = "Package with id " + id + " is returned";
            log.info(mess);
        }
        return p;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete package", description = "Delete package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id deleted")
    public int deletePackageById(@PathVariable(name = "id")Long id) throws NoSuchPackageException {
        try {
            centerService.deletePackageById(id);
            String mess = "Package with id " + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e){
            throw new NoSuchPackageException("Package with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
    @DeleteMapping
    public int deleteAllPackages(){
        centerService.deleteAll();
        return 1;
    }

}
