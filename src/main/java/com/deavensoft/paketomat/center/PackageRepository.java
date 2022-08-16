package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("center")
public interface PackageRepository extends JpaRepository<Package,Long>{
    Optional<Package> findPackageByCode(String code);

}
