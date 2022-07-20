package com.deavensoft.paketomat.courier;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get couriers", description = "Get all couriers")
    @ApiResponse(responseCode = "200", description = "All couriers are returned")
    @ApiResponse(responseCode = "404", description = "Couriers not found")
    @ApiResponse(responseCode = "500", description = "Server fault")
    public List<CourierModel> getAllCouriers(){
        return courierServiceImpl.findAllCouriers();
    }
    @PostMapping
    @Operation(summary = "Add new courier")
    public int saveCourier(@RequestBody CourierModel newCourier){
        courierServiceImpl.saveCourier(newCourier);
        return 1;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get courier", description = "Get courier with specified id")
    public Optional<CourierModel> getCourierById(@PathVariable(name = "id") Long id){
        return Optional.ofNullable(courierServiceImpl.getCourierById(id).orElse(null));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete courier", description = "Delete courier with specified id")
    public int deleteCourierById(@PathVariable(name = "id") Long id){
        courierServiceImpl.deleteCourierById(id);
        return 1;
    }
}
