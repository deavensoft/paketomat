package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;


import java.util.List;
import java.util.Optional;

public interface CityService {

     List<City> getAllCities();
     void save(City c);
     int deleteCity(City c);
     Optional<City> findCityById(Long id);

}
