package com.deavensoft.paketomat.city;

import com.deavensoft.paketomat.center.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;
    @Autowired
    public CityServiceImpl(@Qualifier("city") CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    public List<City> getAllCities(){

        return cityRepository.findAll();
    }
    public void save(City c){
        cityRepository.save(c);
    }
    public int deleteCity(City c){
        cityRepository.delete(c);
        return 1;
    }
    public Optional<City> findCityById(Long id){
        return cityRepository.findById(id);
    }

    public Optional<City>findCityByName(String name)
    {
        return cityRepository.findCityByName(name);
    }



}
