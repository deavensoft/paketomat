package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.deavensoft.paketomat.center.model.Center;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService {
    private CenterRepository centerRepository;


    @Autowired
    public CenterServiceImpl(@Qualifier("center") CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public void initialise() {

    }

    public List<Package> getAllPackages() {
        return centerRepository.findAll();
    }

    public void save(Package p) {
        centerRepository.save(p);
    }

    public Optional<Package> findPackageById(Long id) {
        return centerRepository.findById(id);
    }

    public void deletePackageById(Long id) {
        centerRepository.deleteById(id);
    }



}
