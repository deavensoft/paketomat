package com.deavensoft.paketomat.center;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
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
