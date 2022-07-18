package com.deavensoft.paketomat.center;

import java.util.List;
import java.util.UUID;

public interface iCenter {
    int insertPackage(UUID id,Package newPackage);
    List<Package> getAllPackages();
    default int addPackage(Package newPackage){
        UUID id = UUID.randomUUID();
        return insertPackage(id, newPackage);
    }

}
