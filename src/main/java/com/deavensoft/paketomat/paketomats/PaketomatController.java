package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Paketomat;
import com.deavensoft.paketomat.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.deavensoft.paketomat.center.model.Center.cities;

@AllArgsConstructor
@RestController
@RequestMapping("/api/paketomats")
@Slf4j
public class PaketomatController {
    private PaketomatService paketomatService;
    private CityService cityService;

    @Operation(summary = "Get paketomats", description = "Get all paketomats")
    @ApiResponse(responseCode = "200", description = "All paketomats are returned")
    @GetMapping
    public List<Paketomat> getAllPaketomats(){
        log.info("All paketomats are returned.");
        return paketomatService.getAllPaketomats();
    }

    @Operation(summary = "Add new paketomat")
    @ApiResponse(responseCode = "200", description = "New paketomat added")
    @PostMapping
    public int savePaketomat(@RequestBody Paketomat paketomat){
        log.info("New paketomat added.");
        paketomatService.savePaketomat(paketomat);
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
                    Paketomat p = new Paketomat(c, Paketomat.serialNumber++);
                    savePaketomat(p);
                    c.getPaketomats().add(p);
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
}