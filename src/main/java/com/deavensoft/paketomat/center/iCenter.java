package com.deavensoft.paketomat.center.dao;

import com.deavensoft.paketomat.center.model.Package;

import java.util.List;

public interface iCenter {
    void savePackage(Package newPackage);
    List<Package> getAllPackages();

}
