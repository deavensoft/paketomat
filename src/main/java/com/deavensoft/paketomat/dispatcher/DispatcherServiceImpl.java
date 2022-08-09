package com.deavensoft.paketomat.dispatcher;

import com.deavensoft.paketomat.center.CenterService;
import com.deavensoft.paketomat.center.model.*;
import com.deavensoft.paketomat.center.model.Package;
import com.deavensoft.paketomat.email.EmailDetails;
import com.deavensoft.paketomat.email.EmailService;
import com.deavensoft.paketomat.exceptions.NoSuchCityException;
import com.deavensoft.paketomat.exceptions.NoSuchUserException;
import com.deavensoft.paketomat.exceptions.PaketomatException;
import com.deavensoft.paketomat.paketomats.PaketomatService;
import com.deavensoft.paketomat.user.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class DispatcherServiceImpl implements DispatcherService {

    private final DispatcherRepository dispatcherRepository;
    @Value("${external.api.distance.url}")
    private String url;
    @Value("${external.api.distance.header.host.name}")
    private String hostName;
    @Value("${external.api.distance.header.host.value}")
    private String hostValue;
    @Value("${external.api.distance.header.key.name}")
    private String keyName;
    @Value("${external.api.distance.header.key.value}")
    private String keyValue;
    private final UserService userService;
    private final EmailService emailService;
    private final CenterService centerService;
    private final PaketomatService paketomatService;

    @Value("${paketomat.min-population}")
    private int minPopulation;

    @Value("${dispatcher.distance}")
    private String haversine;

    @Value("${paketomat.size-of-paketomat}")
    private int sizeOfPaketomat;

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
    public void delieverPackage(Package newPackage) throws PaketomatException {
        Optional<User> user = userService.findUserById(newPackage.getUser().getId());
        if(user.isPresent()){
            String[] parts = user.get().getAddress().split(",", 2);
            String cityUser = parts[1];
            findNearestCity(cityUser, newPackage);
            sendMailToCourier();
        }else
            throw new NoSuchUserException("There is no user with id " + newPackage.getUser().getId(), HttpStatus.OK, 200);
    }

    public void findNearestCity(String address, Package newPackage) throws PaketomatException {
        City city = null;
        double minDistance = 1000.0;
        double distance;
        List<City> cities = new ArrayList<>(Center.cities);
        while (true) {
            if(cities.isEmpty()){
                throw new NoSuchCityException("There is no city available", HttpStatus.OK, 200);
            }
            for (City c : cities) {
                if (c.getPopulation() > minPopulation) {
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

    @Override
    public double findDistance(String cityPaketomat, String cityReciever) throws PaketomatException {
        if(cityReciever.contentEquals(cityPaketomat)){
            return 0.0;
        }
        try {
            OkHttpClient client = new OkHttpClient();

            String newUrl = url.replace("{cityReciever}", cityReciever);
            String newURL = newUrl.replace("{cityPaketomat}", cityPaketomat);
            Request request = new Request.Builder()
                   .url(newURL)
                    .get()
                    .addHeader(keyName, keyValue)
                    .addHeader(hostName, hostValue)
                    .build();

            try(Response response = client.newCall(request).execute()){
                log.info("API is called");
                return calculateDistance(response.body().string());
            }
        } catch (IOException e) {
            throw new PaketomatException("Distance cannot be calculated", HttpStatus.INTERNAL_SERVER_ERROR,500);
        }
    }

    public double calculateDistance(String text) {
        if(!text.contains(haversine))
            return 0.0;
        String part = text.substring(text.indexOf(haversine), text.indexOf("greatCircle") - 2);
        String[] parts = part.split(":", 2);
        String distance = parts[1];
        try {
            log.info("Distance is calculated");
            return Double.parseDouble(distance);
        } catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }

    public boolean checkIfAvaiable(Package newPackage, City city) {
        for(Paketomat paketomat : paketomatService.getAllPaketomats()){
            if(city.getId().equals(paketomat.getCity().getId())){
                if (paketomat.getPackages().size() < sizeOfPaketomat) {
                    paketomat.reserveSlot(newPackage);
                    newPackage.setPaketomat(paketomat);
                    centerService.updateStatus(newPackage.getCode(), Status.TO_DISPATCH);
                    log.info("Slot is reserved for package in paketomat in city " + city.getName());
                    return true;
                }
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
        log.info("Mail is sent to courier");
    }
}
