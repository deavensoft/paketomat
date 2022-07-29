package com.deavensoft.paketomat.paketomats;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Paketomat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/paketomats")
@Slf4j
public class PaketomatController {
    private PaketomatService paketomatService;

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
        paketomatService.savePaketomats(paketomat);
        return 1;
    }
//    @PostMapping
//    @Operation(summary = "Add new paketomats")
//    @ApiResponse(responseCode = "200", description = "New paketomats added")
//    public int saveAllPaketomats(List<City> cities){
//        int numberOfPaketomats;
//        for(City c: cities){
//            if(c.getPopulation() > 10000){
//                 numberOfPaketomats = c.getPopulation()/100000 + 1;
//                for(int i = 0; i < numberOfPaketomats; i++){
//                    savePaketomat(new Paketomat(1L,c.getId()));
//                }
//            }
//
//        }
//        return 1;
//    }
}