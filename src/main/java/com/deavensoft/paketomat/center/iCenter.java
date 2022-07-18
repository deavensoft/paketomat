package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface iCenter{
    int insertPackage(UUID id,Package newPackage);
    List<Package> getAllPackages();
    default int addPackage(Package newPackage){
        UUID id = UUID.randomUUID();
        return insertPackage(id, newPackage);
    }

}
