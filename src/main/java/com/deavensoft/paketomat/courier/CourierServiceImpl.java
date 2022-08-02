package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final CenterService centerService;
    private final CityService cityService;
    private List<Package> packagesToDispatch;
    private List<City> citiesToDispatch;
    @Autowired
    public CourierServiceImpl(@Qualifier("courier") CourierRepository courierRepository,
                              CenterService centerService, CityService cityService){
        this.courierRepository = courierRepository;
        this.centerService = centerService;
        this.cityService = cityService;
        this.packagesToDispatch = new ArrayList<>();
        this.citiesToDispatch = new ArrayList<>();
    }

    public List<Courier> findAllCouriers(){
        return courierRepository.findAll();
    }

    public void saveCourier(Courier newCourier){
        courierRepository.save(newCourier);
    }


    public Optional<Courier> getCourierById(Long id){
        return courierRepository.findById(id);
    }

    public void deleteCourierById(Long id){
        courierRepository.deleteById(id);
    }

    public List<Package> getPackagesToDispatch() {
        List<Package> packageList = centerService.getAllPackages();

        for(Package p : packageList){
            if(p.getStatus().equals(Status.TO_DISPATCH))
                packagesToDispatch.add(p);
        }

        return packagesToDispatch;
    }

    public List<City> findCitiesInRadius(City city){
        double maxDistance = 100.0;
        double distance;
        List<City> citiesList = cityService.getAllCities();

        for(City c : citiesList){

        }

        return null;
    }
}
