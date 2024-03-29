package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.dto.PackageDTO;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.courier.dto.CourierDTO;
import com.deavensoft.paketomat.exceptions.NoSuchCourierException;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.mapper.CourierMapper;
import com.deavensoft.paketomat.mapper.PackageMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/couriers")
@Slf4j
public class CourierController {
    private final CourierService courierService;
    static final String MESSAGE = "Courier with id ";
    private final CourierMapper courierMapper;
    private final PackageMapper packageMapper;

    @GetMapping
    @Operation(summary = "Get couriers", description = "Get all couriers")
    @ApiResponse(responseCode = "200", description = "All couriers are returned")
    public List<CourierDTO> getAllCouriers() {

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
    public int saveCourier(@RequestBody CourierDTO newCourier) {
        log.info("New dispatcher is added");
        Courier c = courierMapper.courierDTOToCourier(newCourier);
        courierService.saveCourier(c);

        return 1;
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get courier", description = "Get courier with specified id")
    @ApiResponse(responseCode = "200", description = "Courier with specified id returned")
    public CourierDTO getCourierById(@PathVariable(name = "id") Long id) throws NoSuchCourierException {
        Optional<Courier> c = courierService.getCourierById(id);
        if (c.isEmpty()) {
            throw new NoSuchCourierException("There is no courier with id " + id, HttpStatus.OK, 200);
        } else {
            Courier courier = c.get();
            CourierDTO courierDTO = courierMapper.courierToCourierDTO(courier);
            String mess = MESSAGE + id + " is returned";
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
            String mess = MESSAGE + id + " is deleted";
            log.info(mess);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchCourierException(MESSAGE + id + " can't be deleted", HttpStatus.INTERNAL_SERVER_ERROR, 500);
        }
        return 1;
    }

    @GetMapping(path = "/getPackages/{city}")
    @Operation(summary = "Get packages for courier", description = "Get packages that courier will deliver on his route")
    @ApiResponse(responseCode = "200", description = "All packages that need to be delivered by courier are returned")
    public List<Package> getPackagesForCourier(@PathVariable(name = "city") String city) throws PaketomatException, UnsupportedEncodingException {
        log.info("List with packages that courier has to deliver are returned");
        return courierService.getPackagesForCourier(city);
    }

    @GetMapping(path = "/getOutdatedPackages")
    @Operation(summary = "Get not picked up packages", description = "Get packages that not picked up by user")
    @ApiResponse(responseCode = "200", description = "All packages that not picked up by user will be returned.")
    public List<PackageDTO> getOutdatedPackages(){
        List<PackageDTO> notPickedUpPackages = new ArrayList<>();
        List<Package> packages = courierService.getNotPickedUpPackages();
        for (Package p : packages) {
            PackageDTO pDto = packageMapper.packageToPackageDTO(p);
            notPickedUpPackages.add(pDto);
        }
        log.info("List of returned packages");
        return notPickedUpPackages;
    }

    @GetMapping(path = "/export/{city}")
    @Operation(summary = "Export data packages for courier", description = "Get packages that courier will deliver on his route")
    @ApiResponse(responseCode = "200", description = "All packages that need to be delivered by courier are returned")
    public void exportDispatchedPackages(@PathVariable(name = "city") String city, HttpServletResponse response) throws PaketomatException, IOException {
        courierService.exportToCSV(response, city);
        log.info("Data is successfully exported");
    }

    @Operation(summary = "Get all not picked up packages", description = "Get packages that have not been picked up")
    @ApiResponse(responseCode = "200", description = "Get all packages that have not been picked up from paketomats")
    @GetMapping("/export")
    public void exportOutdatedPackages(HttpServletResponse response) throws IOException {
        courierService.exportOutdatedPackagesToCSV(response);
        log.info("Outdated packages are exported to CSV file");
    }

    @GetMapping(path = "/returnOutdatedPackages")
    @Operation(summary = "Return not picked up packages", description = "Return not picked up packages to the distributive center.")
    @ApiResponse(responseCode = "200", description = "All packages that not picked up by user will be returned to the distributive center.")
    public List<PackageDTO> removeOutDatedPackages() {
        List<PackageDTO> packagesToReturn = new ArrayList<>();
        List<Package> packages = courierService.returnNotPickedUpPackages();
        for (Package p : packages) {
            PackageDTO pDto = packageMapper.packageToPackageDTO(p);
            packagesToReturn.add(pDto);
        }
        log.info("List of returned packages");
        return packagesToReturn;
    }
}
