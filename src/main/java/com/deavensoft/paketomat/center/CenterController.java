package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/packages")
@Slf4j
public class CenterController {

    private final CenterServiceImpl centerServiceImpl;
    @Autowired
    public CenterController(CenterServiceImpl centerServiceImpl)
    {
        this.centerServiceImpl = centerServiceImpl;
    }

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
        centerServiceImpl.save(newPackage);
        log.info("New package added to the database");
        return 1;
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
