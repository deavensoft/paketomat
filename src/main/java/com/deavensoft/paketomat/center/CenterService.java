package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;

import java.util.List;
import java.util.Optional;

public interface CenterService {

    public void initialise();

    public List<Package> getAllPackages();

    public void save(Package packagee);

    public Optional<Package> findPackageById(Long id);

    public void deletePackageById(Long id);

    public List<Package> findAllPackageByStatus(Status status);

}
