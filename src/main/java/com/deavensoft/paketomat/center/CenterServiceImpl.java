package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService{
    private Center center;
    private com.deavensoft.paketomat.center.model.Center distributiveCenter;
    @Autowired
    public CenterServiceImpl(@Qualifier ("center") Center center)
    {
        this.center=center;
        this.distributiveCenter = new com.deavensoft.paketomat.center.model.Center();
        initialise();
    }
    public void initialise(){

    }
    public List<Package> getAllPackages()
    {
      return  center.findAll();
    }

    public void save(Package packagee)
    {
        center.save(packagee);
    }

    public Optional<Package> findPackageById(Long id)
    {
        return center.findById(id);
    }
    public void deletePackageById(Long id)
    {
        center.deleteById(id);
    }



}
