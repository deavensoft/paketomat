package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CitiesDto;
import com.deavensoft.paketomat.city.dto.CityDto;
import com.deavensoft.paketomat.exceptions.CityException;
import com.deavensoft.paketomat.mapper.CityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    @Value("${external.api.city.url}")
    private String url;
    @Value("${external.api.city.header.host.name}")
    private String hostName;
    @Value("${external.api.city.header.host.value}")
    private String hostValue;
    @Value("${external.api.city.header.key.name}")
    private String keyName;
    @Value("${external.api.city.header.key.value}")
    private String keyValue;

    @Value("${external.api.city.start-page}")
    private Integer startPage;

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
    public void checkCities() throws IOException, CityException {

        CitiesDto citiesDto = new ObjectMapper().readValue(doRequest(url).body().string(), CitiesDto.class);
        Integer totalNum = citiesDto.getTotalPages();
        Integer numCities = citiesDto.getTotalCities();
        Integer numFromTable = getAllCities().size();
        if (numFromTable < numCities) {
            try {
                for (int i = startPage; i <= totalNum; i++) {
                    String ii = Integer.toString(i);
                    String newUri = url.replace("{1}", ii);

                    CitiesDto citiesDtoo = new ObjectMapper().readValue(doRequest(newUri).body().string(), CitiesDto.class);
                    List<CityDto> citiess = citiesDtoo.getCities();

                    for (CityDto city : citiess) {

                        save(mapper.cityDtoToCity(city));
                    }

                }
            } catch (IOException e) {
                throw new CityException("Data cannot be imported to database", HttpStatus.LOOP_DETECTED,508);
            }

        } else {
            log.info("Data is imported and it is consistent");
        }

    }
    private Response doRequest(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(keyName, keyValue)
                .addHeader(hostName, hostValue)
                .build();

        return client.newCall(request).execute();
    }
}
