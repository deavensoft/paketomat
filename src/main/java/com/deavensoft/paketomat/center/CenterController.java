package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/packages")
public class CenterController {
    private CenterService centerService;
    @Autowired
    public CenterController(CenterService centerService)
    {
        this.centerService=centerService;
    }

    @GetMapping
    public List<Package> getAllPackages()
    {
        return centerService.getAllPackages();
    }

    @PostMapping
    public int savePackage(@RequestBody Package newPackage)
    {
        centerService.save(newPackage);
        return 1;
    }
    @GetMapping(path = "{id}")
    public Optional<Package> getPackageById(@PathVariable(name = "id") Long id)
    {
        return centerService.findPackageById(id);
    }

    @DeleteMapping(path = "{id}")
    public int deletePackageById(@PathVariable(name = "id")Long id)
    {
        centerService.deletePackageById(id);
        return 1;
    }


}
