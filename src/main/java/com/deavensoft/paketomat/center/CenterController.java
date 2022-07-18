package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CenterController {
    private CenterService centerService;

    @Autowired
    public CenterController (CenterService centerService){
        this.centerService = centerService;
    }
    @GetMapping
    public List<Package> getAllPackages(){
        return centerService.getAllPackages();
    }
    @PostMapping
    public void addPackage(Package newPackage){
        centerService.addPackage(newPackage);
    }
}
