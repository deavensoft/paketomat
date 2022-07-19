package com.deavensoft.paketomat.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/couriers")
public class CourierController {
    private CourierServiceImpl courierServiceImpl;
    @Autowired
    public CourierController(CourierServiceImpl courierServiceImpl){
        this.courierServiceImpl = courierServiceImpl;
    }
    @GetMapping
    public List<CourierModel> getAllCouriers(){
        return courierServiceImpl.findAllCouriers();
    }
    @PostMapping
    public int saveCourier(@RequestBody CourierModel newCourier){
        courierServiceImpl.saveCourier(newCourier);
        return 1;
    }

    @GetMapping(path = "{id}")
    public Optional<CourierModel> getCourierById(@PathVariable(name = "id") Long id){
        return Optional.ofNullable(courierServiceImpl.getCourierById(id).orElse(null));
    }

    @DeleteMapping(path = "{id}")
    public int deleteCourierById(@PathVariable(name = "id") Long id){
        courierServiceImpl.deleteCourierById(id);
        return 1;
    }
}
