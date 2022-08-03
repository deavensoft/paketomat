package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.courier.dto.CourierDTO;
import com.deavensoft.paketomat.exceptions.NoSuchCourierException;
import com.deavensoft.paketomat.mapper.CourierMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/couriers")
@Slf4j
public class CourierController {

    private CourierService courierService;

    private CourierMapper courierMapper;

    @GetMapping
    @Operation(summary = "Get couriers", description = "Get all couriers")
    @ApiResponse(responseCode = "200", description = "All couriers are returned")
    public List<CourierDTO> getAllCouriers(){

        List<Courier> couriers = courierService.findAllCouriers();
        List<CourierDTO> courierDTOS = new ArrayList<>();

        for (Courier courier : couriers) {
            courierDTOS.add(courierMapper.courierToCourierDTO(courier));
        }
        log.info("All couriers are returned");
        return courierDTOS;
    }

    @PostMapping
    @Operation(summary = "Add new courier")
    @ApiResponse(responseCode = "200", description = "New courier added")
    public int saveCourier(@RequestBody Courier newCourier){
        log.info("New dispatcher is added");
        courierService.saveCourier(newCourier);

        return 1;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get courier", description = "Get courier with specified id")
    @ApiResponse(responseCode = "200", description = "Courier with specified id returned")
    public CourierDTO getCourierById(@PathVariable(name = "id") Long id) throws NoSuchCourierException {
        Optional<Courier> c = courierService.getCourierById(id);


        if(c.isEmpty()){
            throw new NoSuchCourierException("There is no courier with id " + id, HttpStatus.OK, 200);
        } else{
            Courier courier = c.get();
            CourierDTO courierDTO = courierMapper.courierToCourierDTO(courier);
            String mess = "Courier with id " + id + " is returned";
            log.info(mess);

            return courierDTO;
        }

    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete courier", description = "Delete courier with specified id")
    @ApiResponse(responseCode = "200", description = "Courier with specified id deleted")
    public int deleteCourierById(@PathVariable(name = "id") Long id) throws NoSuchCourierException {
        try {
            courierService.deleteCourierById(id);
            String mess = "Courier with id " + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e){
            throw new NoSuchCourierException("Courier with id " + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }
}
