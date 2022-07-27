package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.city.dto.CitiesDto;
import com.deavensoft.paketomat.city.dto.CityDto;
import com.deavensoft.paketomat.mapper.CitiesMapper;
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
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    @Value("${spring.url}")
    private   String url;
    @Value("${spring.header.host.name}")
    private String hostName;
    @Value("${spring.header.host.value}")
    private String hostValue;
    @Value("${spring.header.key.name}")
    private String keyName;
    @Value("${spring.header.key.value}")
    private String keyValue;
    private final CitiesMapper mapper;

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
        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(doRequest(url)).execute();

        CitiesDto citiesDto = new ObjectMapper().readValue(response.body().string(), CitiesDto.class);
        List<CityDto> cities = citiesDto.getCities();
        Integer totalNum = citiesDto.getTotalPages();
        Integer numCities = citiesDto.getTotalCities();
        Integer numFromTable = getAllCities().size();
        client.cancel(response);
        if (numFromTable < numCities) {

            for (CityDto city : cities) {

                save(mapper.cityDtoToCity(city));
            }

            for (int i = 2; i <= totalNum; i++) {
                String ii = Integer.toString(i);
                String urlPagination = url;
                String newUri = url.replace("{1}", ii);

                Response responsePaginate = client.newCall(doRequest(newUri)).execute();

                CitiesDto citiesDtoo = new ObjectMapper().readValue(responsePaginate.body().string(), CitiesDto.class);
                List<CityDto> citiess = citiesDtoo.getCities();

                for (CityDto city : citiess) {

                    save(mapper.cityDtoToCity(city));

                }

            }
        } else {
            return "Data is consistent with database";
        }
        return response.body().string();
    }
    private Request doRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(keyName, keyValue)
                .addHeader(hostName, hostValue)
                .build();
        return request;
    }
}
