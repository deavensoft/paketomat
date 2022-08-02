package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.model.*;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import com.deavensoft.paketomat.user.UserService;
import lombok.AllArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

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
    private EmailService emailService;

    private  static final int MIN_POPULATION = 10000;
    private  static final int SIZE_OF_PAKETOMAT = 5;

    @Autowired
    public DispatcherServiceImpl(@Qualifier("dispatcher") DispatcherRepository dispatcherRepository,
                                 UserService userService, EmailService emailService) {
        this.dispatcherRepository = dispatcherRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    public List<Dispatcher> findAllDispatchers() {
        return dispatcherRepository.findAll();
    }

    public void saveDispatcher(Dispatcher newDispatcher) {
        dispatcherRepository.save(newDispatcher);
    }

    public Optional<Dispatcher> findDispatcherById(Long id) {
        return dispatcherRepository.findById(id);
    }

    public void deleteDispatcherById(Long id) {
        dispatcherRepository.deleteById(id);
    }

    @Override
    public void delieverPackage(Package newPackage) throws IOException, NoSuchUserException {
        Optional<User> user = userService.findUserById(newPackage.getUser().getId());
        if(user.isPresent()){
            String[] parts = user.get().getAddress().split(",", 2);
            String cityUser = parts[1];
            findNearestCity(cityUser, newPackage);
            sendMailToCourier();
        }else
            throw new NoSuchUserException("There is no user with id " + newPackage.getUser().getId(), HttpStatus.NOT_FOUND, 404);
    }

    public void findNearestCity(String address, Package newPackage) throws IOException {
        City city = null;
        double minDistance = 1000.0;
        double distance;
        ArrayList<City> cities = new ArrayList<>(Center.cities);
        while (true) {
            for (City c : cities) {
                if (c.getPopulation() > MIN_POPULATION) {
                    distance = findDistance(c.getName(), address);
                    if (distance < minDistance) {
                        minDistance = distance;
                        city = c;
                    }
                }
            }
            if (city!= null && checkIfAvaiable(newPackage, city))
                break;
            cities.remove(city);
            minDistance = 1000.0;
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
        return Double.parseDouble(haversine);
    }

    public boolean checkIfAvaiable(Package newPackage, City city) {
        for (Paketomat paketomat : city.getPaketomats()) {
            if (paketomat.getPackages().size() < SIZE_OF_PAKETOMAT) {
                paketomat.reserveSlot(newPackage);
                return true;
            }
        }
        return false;
    }

    public void sendMailToCourier() {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("courierpaketomat@gmail.com");
        emailDetails.setMsgBody("Hello, new package should be delivered");
        emailDetails.setSubject("Package delivering");
        Map<String, Object> model = new HashMap<>();
        model.put("msgBody", emailDetails.getMsgBody());
        emailService.sendMailWithTemplate(emailDetails, model);
    }
}
