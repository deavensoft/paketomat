package com.deavensoft.paketomat.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {
    private Courier courier;
    @Autowired
    public CourierServiceImpl(@Qualifier("courier") Courier courier){
        this.courier = courier;
    }

    public List<CourierModel> findAllCouriers(){
        return courier.findAll();
    }

    public void saveCourier(CourierModel newCourier){
        courier.save(newCourier);
    }


    public Optional<CourierModel> getCourierById(Long id){
        return courier.findById(id);
    }

    public void deleteCourierById(Long id){
        courier.deleteById(id);
    }
}
