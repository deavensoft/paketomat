package com.deavensoft.paketomat.center;

import java.util.List;

public interface iCenter {
    void savePackage(Package newPackage);
    List<Package> getAllPackages();

}
