package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.exceptions.NoSuchPackageException;
import com.deavensoft.paketomat.paketomats.dto.PaketomatDTO;
import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Paketomat;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.mapper.PaketomatMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deavensoft.paketomat.center.model.Center.cities;

@AllArgsConstructor
@RestController
@RequestMapping("/api/paketomats")
@Slf4j
public class PaketomatController {
    private PaketomatService paketomatService;

    private  PaketomatServiceImpl paketomatServiceImpl;

    private CityService cityService;

    private PaketomatMapper paketomatMapper;

    private CenterService centerService;

    @Operation(summary = "Get paketomats", description = "Get all paketomats")
    @ApiResponse(responseCode = "200", description = "All paketomats are returned")
    @GetMapping
    public List<PaketomatDTO> getAllPaketomats(){

        List<Paketomat> paketomats = paketomatService.getAllPaketomats();
        List<PaketomatDTO> paketomatDTOS = new ArrayList<>();

        for (Paketomat paketomat : paketomats){
            paketomatDTOS.add(paketomatMapper.paketomatToPaketomatDTO(paketomat));
        }
        log.info("All paketomats are returned.");
        return paketomatDTOS;

    }

    @Operation(summary = "Add new paketomat")
    @ApiResponse(responseCode = "200", description = "New paketomat added")
    @PostMapping
    public int savePaketomat(@RequestBody PaketomatDTO paketomat){
        log.info("New paketomat added.");
        Paketomat pa = paketomatMapper.paketomatDTOToPaketomat(paketomat);
        paketomatServiceImpl.savePaketomat(pa);
        return 1;
    }

    @PostMapping("/saveAll")
    @Operation(summary = "Add new paketomats")
    @ApiResponse(responseCode = "200", description = "New paketomats added")
    public int createAllPaketomatsInCountry(){
        int numberOfPaketomats;
        cities =  cityService.getAllCities();
        for(City c: cities){
            if(c.getPopulation() > 10000){
                 numberOfPaketomats = c.getPopulation()/100000 + 1;
                for(int i = 0; i < numberOfPaketomats; i++){
                    PaketomatDTO pa = new PaketomatDTO();
                    pa.setCity(c);
                    pa.setPackages(new ArrayList<>());
                    savePaketomat(pa);
                }
            }
        }
        return 1;
    }

    @Operation(summary = "Deleting all paketomats")
    @ApiResponse(responseCode = "200", description = "All paketomats deleted.")
    @DeleteMapping
    public int deleteAll(){
        log.info("All paketomats are deleted");
        paketomatService.deleteAll();
        return 1;
    }


    @PostMapping(path = "/userPackage/{code}")
    @Operation(summary = "Move package to user")
    @ApiResponse(responseCode = "200", description = "Package is delivered to user")
    public void userPackage(@PathVariable(name = "code") Long code) throws NoSuchPackageException {
        Optional<Package> userPackage = centerService.findPackageByCode(code);

        if (userPackage.isEmpty()){
            throw new NoSuchPackageException("There is no package with code " + code, HttpStatus.OK, 200);
        }else {
            List<PaketomatDTO> paketomats = getAllPaketomats();
            for (PaketomatDTO paketomat : paketomats) {
                Package pa = userPackage.get();
                pa.setPaketomat(null);
                centerService.updateStatus(code, Status.DELIVERED);
            }
        }
    }
}