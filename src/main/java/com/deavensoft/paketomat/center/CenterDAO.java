package com.deavensoft.paketomat.center.dao;

import com.deavensoft.paketomat.center.model.Center;
import com.deavensoft.paketomat.center.model.Package;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("center")
public class CenterDAO implements iCenter{
    ArrayList<Package> packages = new ArrayList<>();

    private Package newPackage;

    public CenterDAO(Package newPackage){
        this.newPackage = newPackage;
    }

    @Override
    public void savePackage(Package newPackage) {
        packages.add(newPackage);
    }

    @Override
    public List<Package> getAllPackages() {
        return packages;
    }
}
