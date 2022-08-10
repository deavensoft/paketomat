package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paid;
import com.deavensoft.paketomat.center.model.Status;

import java.util.List;
import java.util.Optional;

public interface CenterService {

     List<Package> getAllPackages();

     void save(Package packagee);

     Optional<Package> findPackageById(Long id);

     void deletePackageById(Long id);

     void deleteAll();
     void updateStatus(Long id, Status status);
     void payment(Long id, Paid paid);


}
