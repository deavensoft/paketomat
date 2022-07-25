package com.deavensoft.paketomat.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {
    private CourierRepository courierRepository;
    @Autowired
    public CourierServiceImpl(@Qualifier("courier") CourierRepository courierRepository){
        this.courierRepository = courierRepository;
    }

    public List<CourierModel> findAllCouriers(){
        return courierRepository.findAll();
    }

    public void saveCourier(CourierModel newCourier){
        courierRepository.save(newCourier);
    }


    public Optional<CourierModel> getCourierById(Long id){
        return courierRepository.findById(id);
    }

    public void deleteCourierById(Long id){
        courierRepository.deleteById(id);
    }

}
