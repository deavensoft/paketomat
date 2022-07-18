package com.deavensoft.paketomat.center;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("center")
public class CenterDAO implements iCenter{
    ArrayList<Package> packages = new ArrayList<>();

    @Override
    public int insertPackage(UUID id, Package newPackage) {
        packages.add(new Package(id,newPackage.getStatus()));
        return 1;
    }

    @Override
    public List<Package> getAllPackages() {
        return packages;
    }


}
