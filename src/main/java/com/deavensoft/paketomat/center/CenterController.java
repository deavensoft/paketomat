package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
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
    public List<Package> getAllPackages()
    {
        return centerServiceImpl.getAllPackages();
    }

    @PostMapping
    public int savePackage(@RequestBody Package newPackage)
    {
        centerServiceImpl.save(newPackage);
        return 1;
    }
    @GetMapping(path = "{id}")
    public Optional<Package> getPackageById(@PathVariable(name = "id") Long id)
    {
        return centerServiceImpl.findPackageById(id);
    }

    @DeleteMapping(path = "{id}")
    public int deletePackageById(@PathVariable(name = "id")Long id)
    {
        centerServiceImpl.deletePackageById(id);
        return 1;
    }


}
