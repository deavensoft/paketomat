package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/packages")
public class CenterController {
    private CenterServiceImpl centerServiceImpl;
    @Autowired
    public CenterController(CenterServiceImpl centerServiceImpl)
    {
        this.centerServiceImpl = centerServiceImpl;
    }

    @GetMapping
    @Operation(summary = "Get packages", description = "Get all packages")
    @ApiResponse(responseCode = "200", description = "All packages are returned")
    @ApiResponse(responseCode = "404", description = "Packages not found")
    @ApiResponse(responseCode = "500", description = "Server fault")
    public List<Package> getAllPackages()
    {
        return centerServiceImpl.getAllPackages();
    }

    @PostMapping
    @Operation(summary = "Add new package", description = "Add new package to the distributive center")
    public int savePackage(@RequestBody Package newPackage)
    {
        centerServiceImpl.save(newPackage);
        return 1;
    }
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get package", description = "Get package with specified id")
    public Optional<Package> getPackageById(@PathVariable(name = "id") Long id)
    {
        return centerServiceImpl.findPackageById(id);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete package", description = "Delete package with specified id")
    public int deletePackageById(@PathVariable(name = "id")Long id)
    {
        centerServiceImpl.deletePackageById(id);
        return 1;
    }


}
