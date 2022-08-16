package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;

import java.util.List;
import java.util.Optional;

public interface PackageService {

     List<Package> getAllPackages();

     void save(Package pa);

     Optional<Package> findPackageById(Long id);

     void deletePackageById(Long id);

     void deleteAll();
     void updateStatus(Long id, Status status);
     void payment(Long id, Paid paid) throws NoSuchPackageException;

     Optional<Package> findPackageByCode(Long code);


}
