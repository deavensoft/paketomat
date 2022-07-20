package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("center")
public interface CenterRepository extends JpaRepository<Package,Long>{
}
