package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.model.City;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.center.model.Paketomat;
import com.deavensoft.paketomat.center.model.User;
import com.deavensoft.paketomat.city.CityService;
import com.deavensoft.paketomat.user.UserService;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DispatcherServiceImpl implements DispatcherService {
    private DispatcherRepository dispatcherRepository;
    @Value("${spring.external.api.distance.url}")
    private String url;
    @Value("${spring.external.api.distance.header.host.name}")
    private String hostName;
    @Value("${spring.external.api.distance.header.host.value}")
    private String hostValue;
    @Value("${spring.external.api.distance.header.key.name}")
    private String keyName;
    @Value("${spring.external.api.distance.header.key.value}")
    private String keyValue;


    private UserService userService;

    private CityService cityService;

    @Autowired
    public DispatcherServiceImpl(@Qualifier("dispatcher") DispatcherRepository dispatcherRepository, UserService userService,
                                 CityService cityService) {
        this.dispatcherRepository = dispatcherRepository;
        this.userService = userService;
        this.cityService = cityService;
    }

    public List<DispatcherModel> findAllDispatchers() {
        return dispatcherRepository.findAll();
    }

    public void saveDispatcher(DispatcherModel newDispatcher) {
        dispatcherRepository.save(newDispatcher);
    }

    public Optional<DispatcherModel> findDispatcherById(Long id) {
        return dispatcherRepository.findById(id);
    }

    public void deleteDispatcherById(Long id) {
        dispatcherRepository.deleteById(id);
    }

    @Override
    public void delieverPackage(Package newPackage) throws IOException {
        User user = userService.findUserById(newPackage.getReciever()).get();
        String[] parts = user.getAddress().split(",", 2);
        String cityUser = parts[1];
        findNearestCity(cityUser, newPackage);
    }

    public void findNearestCity(String address, Package newPackage) throws IOException {
        City city = null;
        double minDistance = 1000.0, distance = 1.0;
        List<City> cities = cityService.getAllCities();
        while (true) {

            for (City c : cities) {
                System.out.println("grad " + c.getName());
                if (c.getPopulation() > 10000) {
                    System.out.println("populacija: " + c.getPopulation());
                    distance = findDistance(c.getName(), address);
                    if (distance < minDistance) {
                        System.out.println("nova minimalna distanca: " + minDistance);
                        minDistance = distance;
                        city = c;
                    }
                }
            }

            if (checkIfAvaiable(newPackage, city))
                break;
            cities.remove(city);
            System.out.println("while petlja");
        }

    }

    public double findDistance(String cityPaketomat, String cityReciever) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String newUrl = url.replace("{cityReciever}", cityReciever);
        String newURL = newUrl.replace("{cityPaketomat}", cityPaketomat);
        Request request = new Request.Builder()
                .url(newURL)
                .get()
                .addHeader(keyName, keyValue)
                .addHeader(hostName, hostValue)
                .build();

        Response response = client.newCall(request).execute();
        return calculateDistance(response.body().string());
    }

    public double calculateDistance(String text) {
        String part = text.substring(text.indexOf("haversine"), text.indexOf("greatCircle") - 2);
        String[] parts = part.split(":", 2);
        String haversine = parts[1];
        return Double.valueOf(haversine);
    }

    public boolean checkIfAvaiable(Package newPackage, City city) {
        System.out.println("ulazi");
        System.out.println("prosledjen grad: " + city.getName() + ", broj paketomata u gradu: " + city.getPaketomats().size());
        for (Paketomat paketomat : city.getPaketomats()) {
            System.out.println("ulazi ovde!!///////////////////////////////////////////////////////////////////////////////////////////////////////");
            if (paketomat.getPackages().size() < 5) {
                paketomat.reserveSlot(newPackage);
                System.out.println("rezervisano");
                return true;
            }
        }
        return false;
    }
}
