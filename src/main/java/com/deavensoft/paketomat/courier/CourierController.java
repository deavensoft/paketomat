package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.exceptions.NoSuchCourierException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/couriers")
@Slf4j
public class CourierController {

    private CourierServiceImpl courierServiceImpl;
    @Autowired
    public CourierController(CourierServiceImpl courierServiceImpl){
        this.courierServiceImpl = courierServiceImpl;
    }
    @GetMapping
    @Operation(summary = "Get couriers", description = "Get all couriers")
    @ApiResponse(responseCode = "200", description = "All couriers are returned")
    public List<CourierModel> getAllCouriers(){
        log.info("All couriers are returned");
        return courierServiceImpl.findAllCouriers();
    }
    @PostMapping
    @Operation(summary = "Add new courier")
    @ApiResponse(responseCode = "200", description = "New courier added")
    public int saveCourier(@RequestBody CourierModel newCourier){
        log.info("New dispatcher is added");
        courierServiceImpl.saveCourier(newCourier);
        return 1;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get courier", description = "Get courier with specified id")
    @ApiResponse(responseCode = "200", description = "Courier with specified id returned")
    public Optional<CourierModel> getCourierById(@PathVariable(name = "id") Long id) throws NoSuchCourierException {
        Optional<CourierModel> c = courierServiceImpl.getCourierById(id);
        if(c.isEmpty()){
            throw new NoSuchCourierException("There is no courier with id " + id, HttpStatus.NOT_FOUND, 404);
        } else{
            String mess = "Courier with id " + id + " is returned";
            log.info(mess);
        }
        return c;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete courier", description = "Delete courier with specified id")
    @ApiResponse(responseCode = "200", description = "Courier with specified id deleted")
    public int deleteCourierById(@PathVariable(name = "id") Long id) throws NoSuchCourierException {
        try {
            courierServiceImpl.deleteCourierById(id);
            String mess = "Courier with id " + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e){
            throw new NoSuchCourierException("Courier with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
}
