package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CityDto;
import com.deavensoft.paketomat.city.scheduler.CityIntegration;
import com.deavensoft.paketomat.exceptions.NoSuchCityException;
import com.deavensoft.paketomat.mapper.CityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/cities")
public class CityController {
    private final CityService cityServiceImpl;
    private final CityMapper mapper;
    private final CityIntegration cityIntegration;

    @GetMapping
    @Operation(summary = "Get cities", description = "Get all cities")
    @ApiResponse(responseCode = "200", description = "All cities are returned")
    public List<CityDto> getAllCities() {
        List<City> cities = cityServiceImpl.getAllCities();
        List<CityDto> cityDtos = new ArrayList<>();

        for (City city : cities) {
            cityDtos.add(mapper.cityToCityDto(city));
        }
        log.info("All cities are returned");
        return cityDtos;
    }

    @PostMapping
    @Operation(summary = "Add new city", description = "Add new city to the database")
    @ApiResponse(responseCode = "200", description = "New city added")
    public void save(@RequestBody CityDto c) {
        City city = mapper.cityDtoToCity(c);
        cityServiceImpl.save(city);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get city", description = "Get city with specified id")
    @ApiResponse(responseCode = "200", description = "City with specified id returned")
    public CityDto findCityById(@PathVariable(name = "id") long id) throws NoSuchCityException {
        Optional<City> city = cityServiceImpl.findCityById(id);

        if (city.isEmpty()) {
            throw new NoSuchCityException("There is no city with id " + id, HttpStatus.OK, 200);
        } else {
            City city1 = city.get();
            CityDto cityDto = mapper.cityToCityDto(city1);
            String mss = "City with id " + id + "is returned";
            log.info(mss);
            return cityDto;
        }
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete city", description = "Delete city with specified id")
    @ApiResponse(responseCode = "200", description = "City with specified id deleted")
    public int deleteCity(@PathVariable(name = "id") Long id) throws NoSuchCityException {
        Optional<City> c = cityServiceImpl.findCityById(id);

        if (c.isEmpty()) {
            throw new NoSuchCityException("There is no city with id " + id, HttpStatus.OK, 200);
        } else {
            City city = c.get();
            cityServiceImpl.deleteCity(city);
            log.info("City with id " + id + " is deleted");
            return 1;
        }
    }

    @GetMapping(path = "/check")
    @Operation(summary = "Import cities", description = "Import cities in Serbia to database")
    public String checkCities() {
        Integer numFromTable = getAllCities().size();
        if (numFromTable == 0) {
            return cityIntegration.importCities();
        }

        return "Up-to-date!";
    }
}
