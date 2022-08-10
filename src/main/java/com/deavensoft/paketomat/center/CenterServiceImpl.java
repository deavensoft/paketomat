package com.deavensoft.paketomat.center;

import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CenterServiceImpl implements CenterService {
    private final CenterRepository centerRepository;

    @Autowired
    public CenterServiceImpl(@Qualifier("center") CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
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

    @Override
    public void deleteAll() {
        centerRepository.deleteAll();
    }

    @Override
    public void updateStatus(Long code, Status status) {
        List<Package> packages = centerRepository.findAll();
        for (Package p : packages) {
            if(p.getCode().equals(code)){
                p.setStatus(status);
                centerRepository.save(p);
            }
        }
    }

    public Optional<Package> findPackageByCode(Long code) {
        return centerRepository.findPackageByCode(code);
    }

}
