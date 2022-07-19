package com.deavensoft.paketomat.courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/couriers")
public class CourierController {
    private CourierService courierService;
    @Autowired
    public CourierController(CourierService courierService){
        this.courierService = courierService;
    }
    @GetMapping
    public List<CourierModel> getAllCouriers(){
        return courierService.findAllCouriers();
    }
    @PostMapping
    public int saveCourier(@RequestBody CourierModel newCourier){
        courierService.saveCourier(newCourier);
        return 1;
    }

    @GetMapping(path = "{id}")
    public Optional<CourierModel> getCourierById(@PathVariable(name = "id") Long id){
        return Optional.ofNullable(courierService.getCourierById(id).orElse(null));
    }

    @DeleteMapping(path = "{id}")
    public int deleteCourierById(@PathVariable(name = "id") Long id){
        courierService.deleteCourierById(id);
        return 1;
    }
}
