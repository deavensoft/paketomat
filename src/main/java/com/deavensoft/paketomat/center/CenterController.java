package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailServiceImpl;
import com.deavensoft.paketomat.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("api/packages")
@Slf4j
@RequiredArgsConstructor
public class CenterController {

    private final CenterServiceImpl centerServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final UserServiceImpl userServiceImpl;


    @GetMapping
    @Operation(summary = "Get packages", description = "Get all packages")
    @ApiResponse(responseCode = "200", description = "All packages are returned")
    public List<Package> getAllPackages()
    {
        log.info("All packages are returned");
        return centerServiceImpl.getAllPackages();
    }

    @PostMapping
    @Operation(summary = "Add new package", description = "Add new package to the distributive center")
    @ApiResponse(responseCode = "200", description = "New package added")
    public int savePackage(@RequestBody Package newPackage)
    {
        newPackage.setStatus(Status.NEW);
        centerServiceImpl.save(newPackage);
        log.info("New package added to the database");

        Optional<User> user = userServiceImpl.findUserById(newPackage.getReciever());
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
            emailServiceImpl.sendMailWithTemplate(emailDetails, model);
            return 1;
        }
        return -1;

    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get package", description = "Get package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id returned")
    public Optional<Package> getPackageById(@PathVariable(name = "id") Long id)
    {
        Optional<Package> p = centerServiceImpl.findPackageById(id);
        if(p.isEmpty()){
            String mess = "There is no user with id " + id;
            log.info(mess);
        } else{
            String mess = "Package with id " + id + " is returned";
            log.info(mess);
        }
        return p;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete package", description = "Delete package with specified id")
    @ApiResponse(responseCode = "200", description = "Package with specified id deleted")
    public int deletePackageById(@PathVariable(name = "id")Long id)
    {
        try {
            centerServiceImpl.deletePackageById(id);
            String mess = "Package with id " + id + " is deleted";
            log.info(mess);
        } catch (NoSuchElementException e){
            String mess = "There is no user with id " + id;
            log.error(mess);
        }
        return 1;
    }


}
