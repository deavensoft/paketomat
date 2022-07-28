package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CitiesDto;
import com.deavensoft.paketomat.city.dto.CityDto;
import com.deavensoft.paketomat.mapper.CityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/cities")

public class CityController {
    @Autowired
    private final CityService cityServiceImpl;
    @Value("${spring.external.api.city.url}")
    private String url;
    @Value("${spring.external.api.city.header.host.name}")
    private String hostName;
    @Value("${spring.external.api.city.header.host.value}")
    private String hostValue;
    @Value("${spring.external.api.city.header.key.name}")
    private String keyName;
    @Value("${spring.external.api.city.header.key.value}")
    private String keyValue;
    private final CityMapper mapper;

    @GetMapping
    @Operation(summary = "Get cities", description = "Get all cities")
    @ApiResponse(responseCode = "200", description = "All cities are returned")
    public List<City> getAllCities() {
        return cityServiceImpl.getAllCities();
    }

    @PostMapping
    @Operation(summary = "Add new city", description = "Add new city to the database")
    @ApiResponse(responseCode = "200", description = "New city added")
    public void save(@RequestBody City c) {
        cityServiceImpl.save(c);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get city", description = "Get city with specified id")
    @ApiResponse(responseCode = "200", description = "City with specified id returned")
    public Optional<City> findCityById(@PathVariable(name = "id") long id) {
        return cityServiceImpl.findCityById(id);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete city", description = "Delete city with specified id")
    @ApiResponse(responseCode = "200", description = "City with specified id deleted")
    public int deleteCity(@PathVariable(name = "id") Long id) {
        Optional<City> c = findCityById(id);
        if (c.isEmpty())
           log.info("There is no city with id:" + id);
        if(!c.isEmpty()) {
            cityServiceImpl.deleteCity(c.get());
            log.info("City deleted");
        }
        return 1;
    }

    @GetMapping(path = "/check")
    public String checkCities() throws IOException {

        CitiesDto citiesDto = new ObjectMapper().readValue(doRequest(url).body().string(), CitiesDto.class);
        List<CityDto> cities = citiesDto.getCities();
        Integer totalNum = citiesDto.getTotalPages();
        Integer numCities = citiesDto.getTotalCities();
        Integer numFromTable = getAllCities().size();
        if (numFromTable <= numCities) {

            for (CityDto city : cities) {

                save(mapper.cityDtoToCity(city));
           }

            for (int i = 2; i <= totalNum; i++) {
                String ii = Integer.toString(i);
                String newUri = url.replace("{1}", ii);

                CitiesDto citiesDtoo = new ObjectMapper().readValue(doRequest(newUri).body().string(), CitiesDto.class);
                List<CityDto> citiess = citiesDtoo.getCities();

                for (CityDto city : citiess) {

                    save(mapper.cityDtoToCity(city));

                }

            }
        } else {
            return "Data is consistent with database";
        }
        return doRequest(url).body().string();
    }
    private Response doRequest(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(keyName, keyValue)
                .addHeader(hostName, hostValue)
                .build();

        Response response;

        return response=client.newCall(request).execute();
    }
}
