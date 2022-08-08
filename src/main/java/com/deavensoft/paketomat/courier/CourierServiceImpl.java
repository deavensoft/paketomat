package com.deavensoft.paketomat.courier;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Status;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.dispatcher.DispatcherService;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;
    private final CenterService centerService;
    private final CityService cityService;
    private final DispatcherService dispatcherService;
    private final List<Package> packagesToDispatch = new ArrayList<>();
    private final List<City> citiesToDispatch = new ArrayList<>();

    public List<Courier> findAllCouriers() {
        return courierRepository.findAll();
    }

    public void saveCourier(Courier newCourier) {
        courierRepository.save(newCourier);
    }


    public Optional<Courier> getCourierById(Long id) {
        return courierRepository.findById(id);
    }

    public void deleteCourierById(Long id) {
        courierRepository.deleteById(id);
    }

    @Override
    public List<Package> getPackagesForCourier(String city) throws PaketomatException {
        getPackagesToDispatch();
        findCitiesInRadius(city);
        return deliverPackageInPaketomat(filterPackagesToDispatch(packagesToDispatch,citiesToDispatch));
    }

    public List<Package> getPackagesToDispatch() {
        List<Package> packageList = centerService.getAllPackages();
        packagesToDispatch.clear();

        for (Package p : packageList) {
            if (p.getStatus().equals(Status.TO_DISPATCH)) {
                packagesToDispatch.add(p);
            }
        }
        log.info("List with packages TO_DISPATCH is made");
        return packageList;
    }

    public List<City> findCitiesInRadius(String city) throws PaketomatException {
        double maxDistance = 100.0;
        double distance;
        List<City> citiesList = cityService.getAllCities();
        citiesToDispatch.clear();

        for (City c : citiesList) {
            distance = dispatcherService.findDistance(city, c.getName());
            if (distance <= maxDistance) {
                citiesToDispatch.add(c);
            }
        }
        log.info("List with cities in 100km radius from city " + city + " is made");
        return citiesList;
    }

    public List<Package> filterPackagesToDispatch(List<Package> packagesToDispatch, List<City> citiesToDispatch ) {
        List<Package> packages = new ArrayList<>();
        for (Package p : packagesToDispatch) {
            String city = p.getPaketomat().getCity().getName();
            if (isCityInList(city))
                packages.add(p);
        }
        log.info("Packages are filtered so courier can deliver them");
        return packages;
    }

    public boolean isCityInList(String city) {
        for (City c : citiesToDispatch) {
            if (c.getName().equalsIgnoreCase(city.trim())) {
                return true;
            }
        }
        return false;
    }

    public List<Package> deliverPackageInPaketomat(List<Package> packages) {
        for (Package p : packages) {
            p.setStatus(Status.IN_PAKETOMAT);
        }
        log.info("Packages are in paketomat and are ready for delivery");
        return packages;
    }
}
