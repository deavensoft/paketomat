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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/cities")
public class CityController {
    private final CityServiceImpl cityServiceImpl;
    private final CitiesMapper mapper;

    @Autowired
    public CityController(CityServiceImpl cityServiceImpl, CitiesMapper mapper) {
        this.cityServiceImpl = cityServiceImpl;
        this.mapper = mapper;
    }

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
            System.out.println("there is no city with id:" + id);
        cityServiceImpl.deleteCity(c.get());
        System.out.println("City deleted");
        return 1;
    }

    @GetMapping(path = "/check")
    public String checkCities() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://countries-cities.p.rapidapi.com/location/country/RS/city/list?page=3&per_page=100&population=1501")
                .get()
                .addHeader("X-RapidAPI-Key", "c427cbdd50msh072fe84a375af9cp1f2596jsn0f5919dacf12")
                .addHeader("X-RapidAPI-Host", "countries-cities.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();


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
                String url = "https://countries-cities.p.rapidapi.com/location/country/RS/city/list?page={1}&per_page=100&population=1501";
                String newUri = url.replace("{1}", ii);

                Request requestPaginate = new Request.Builder()
                        .url(newUri)
                        .get()
                        .addHeader("X-RapidAPI-Key", "c427cbdd50msh072fe84a375af9cp1f2596jsn0f5919dacf12")
                        .addHeader("X-RapidAPI-Host", "countries-cities.p.rapidapi.com")
                        .build();

                Response responsePaginate = client.newCall(requestPaginate).execute();

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
}
