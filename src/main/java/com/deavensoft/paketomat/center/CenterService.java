package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.iCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {
    private iCenter center;
    @Autowired
    public CenterService (@Qualifier("center") iCenter iCenter){
        this.center = iCenter;
    }

    public List<Package> getAllPackages(){
        return center.getAllPackages();
    }
    public void addPackage(Package newPackage){
        center.addPackage(newPackage);
    }
}
